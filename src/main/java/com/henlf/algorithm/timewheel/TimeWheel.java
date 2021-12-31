package com.henlf.algorithm.timewheel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 时间轮{@linkplain https://mp.weixin.qq.com/s/0299YIcMrK6Z_DwL9Hw4wQ}
 * @author tanghongfeng
 * @date 2021-08-10 08:55
 */
public class TimeWheel {
    private static final int BUFFER_SIZE = 64;

    /**
     * 轮子
     */
    private Object[] ringBuffer;

    /**
     * 轮子大小
     */
    private int bufferSize;

    /**
     * 任务数量
     */
    private volatile int taskSize;

    /**
     * 启动标识
     */
    private AtomicBoolean start = new AtomicBoolean(false);

    /**
     * 结束标识
     */
    private volatile boolean stop;

    /**
     * 滴答次数
     */
    private AtomicInteger tick = new AtomicInteger(0);

    /**
     * 任务编号 与 任务 映射
     */
    private Map<String, Task> taskMap = new ConcurrentHashMap<>(16);

    /**
     * 线程池，执行 task
     */
    private ExecutorService executorService;

    /**
     * 同步锁
     */
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public TimeWheel(ExecutorService executorService) {
        this.executorService = executorService;
        this.bufferSize = BUFFER_SIZE;
        this.ringBuffer = new Object[this.bufferSize];
    }

    /**
     * 添加新任务
     *
     * 1.根据 waitNum (延期间隔) 计算 index 和 cycleNum， 更新至 task 对象
     * 2.根据 ringBuffer 在 index 位置是否有元素，把 task 添加到 ringBuffer 中，参考 '数组 + 链表' 结构
     * 3.task 添加到时间轮后，更新 taskSize、 taskMap
     * 4.转动 表针
     */
    public void addTask(Task task) {
        try {
            lock.lock();

            int index = mod(task.getWaitNum(), this.bufferSize);
            int cycleNum = cycleNum(task.getWaitNum(), this.bufferSize);

            task.setIndex(index);
            task.setCycleNum(cycleNum);

            Set<Task> newTask = new HashSet<>();
            Set<Task> existTask = (Set<Task>)ringBuffer[index];

            if (null == existTask || existTask.isEmpty()) {
                newTask.add(task);
            } else {
                existTask.add(task);
                newTask = existTask;
            }

            this.taskSize++;

            taskMap.put(task.getTaskId(), task);

            ringBuffer[index] = newTask;
        } finally {
            lock.unlock();
        }

        start();
    }

    /**
     * 转动表针，员工开始工作，开启 TriggerJob 调度
     *
     * 1.基于线程安全，使用 CAS，判断 start 状态
     */
    public void start() {
        if (!start.get()) {
            if (start.compareAndSet(start.get(), true)) {
                System.out.println("start triggerJob thread!");

                Thread td = new Thread(new TriggerJob());
                td.setName("看表师傅");
                td.start();
            }
        }
    }

    /**
     * 停止 时间轮
     *
     * 1. 非 force， 添加 lock 锁，即此时 addTask 不允许执行
     * 2. 非 force， 修改 stop 标识要等到 condition.signal() 之后，即等待所有格子执行完成 (remove 方法 管理 size 计数).
     * 3. 最后，关闭线程池.
     *
     * @param force 是否强制
     */
    public void stop(boolean force) {
        if (force) {
            System.out.println("force stop ring buffer wheel!");

            stop = true;
        } else if (this.taskSize > 0) {
            try {
                lock.lock();

                System.out.println("wait stop ring buffer wheel!");

                condition.await();
                // 等待 size 为 0，即所有时间格上的任务执行完；此行代码上移一行，则死锁，任务不处理、也不移除.
                stop = true;
            } catch (Exception e) {
                System.out.println("stop ring buffer wheel exception: " + e);
            } finally {
                lock.unlock();
            }
        }

        executorService.shutdown();
    }

    /**
     * 移除 表盘一格中的 task
     *
     * 1. task 的 cycleNum 为 0，移除；cycleNum 非 0，cycleNum--，
     * 2. 重置 ringBuffer[index]； 返回 result.
     *
     * @param index
     * @return
     */
    public Set<Task> remove(int index) {
        Set<Task> result = new HashSet<>(16);
        Set<Task> temp   = new HashSet<>(16);

        Set<Task> tasks = (Set<Task>)ringBuffer[index];
        if (null == tasks || tasks.isEmpty()) {
            return null;
        }

        for (Task task : tasks) {
            if (0 == task.getCycleNum()) {
                result.add(task);

                size2Notify();

                taskMap.remove(task.getTaskId());
            } else {
                task.decrementCycleNum();
                temp.add(task);
            }
        }

        ringBuffer[index] = temp;
        return result;
    }

    /**
     * 消费 task 时，计数递减
     *
     * 当表盘上的挂钩数为 0， 发出通知， 非 force 形式 stop() 获得锁，执行后续代码，关闭线程池。
     */
    private void size2Notify() {
        try {
            lock.lock();

            this.taskSize--;

            if (0 == taskSize) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     *  取余（针对 size 为 2 的 N 次方） equals target % mod
     *
     *  想象一个表盘，size 64, 纵正 0，横正 16, 纵负 32， 横负 48
     *
     *  当 tick 为 16 （三点钟位置）， key 为 96（一圈半），size 为 64， 计算结果恰巧为 48.
     **/
    private int mod(int key, int size) {
        // tick 为当前指针转到的位置
        key = key + tick.get();
        return key & (size - 1);
    }

    /**
     * 取模 equals target / mod
     **/
    private int cycleNum(int target, int mod) {
        return target / mod;
    }

    /**
     * 任务 - 客户执行
     *
     * 1.waitNum 取余 为 index
     * 2.waitNum 取模 为 cycleNum
     */
    public static abstract class Task implements Runnable {
        /**
         * 任务编号
         */
        private String taskId;

        /**
         * 等待间隔 - 当前时间多久后执行
         */
        private int waitNum;

        /**
         * 索引 - 放置到转盘上的位置
         */
        private int index;

        /**
         * 圈数 - 转盘上的等待圈数
         */
        private int cycleNum;

        @Override
        public void run() {
            System.out.println("编号为" + taskId + "的挂牌被取下!");
        }

        /**
         * 本轮未执行，圈数减一
         */
        public void decrementCycleNum() {
            cycleNum--;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public int getWaitNum() {
            return waitNum;
        }

        public void setWaitNum(int waitNum) {
            this.waitNum = waitNum;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getCycleNum() {
            return cycleNum;
        }

        public void setCycleNum(int cycleNum) {
            this.cycleNum = cycleNum;
        }
    }

    /**
     * 调度 - 管理 表盘转动
     *
     * 运行状态下:
     * 1. 取出当前 index， cycleNum 为 0 的 task 集合
     * 2. 线程池遍历执行取出的 task
     * 3. 转动指针 ++index，整圈归零
     * 4. tick 数增 1.
     * 5. 滴答阻塞 1s.
     *
     * 停止状态:
     * 1. 打印 停止 标识！
     *
     */
    public class TriggerJob implements Runnable {
        // 表针转动间隔
        private final Integer INTERVAL = 1;

        @Override
        public void run() {
            int index = 0;

            while (!stop) {
                try {
                    Set<Task> tasks = remove(index);
                    tasks.forEach(t -> {
                        executorService.submit(t);
                    });

                    if (++index > bufferSize - 1) {
                        index = 0;
                    }

                    tick.incrementAndGet();
                    TimeUnit.SECONDS.sleep(INTERVAL);
                } catch (InterruptedException e) {
                    System.out.println("trigger job exception:" + e);
                }
            }

            System.out.println("ring buffer stopped!");
        }
    }
}
