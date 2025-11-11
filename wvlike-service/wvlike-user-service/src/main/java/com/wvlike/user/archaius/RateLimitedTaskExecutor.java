package com.wvlike.user.archaius;

/**
 * @Date: 2025/7/1
 * @Author: tuxinwen
 * @Description: 单线程调度线池，并通过控制任务提交频率来达到 每秒最多执行 10 个任务
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.*;

public class RateLimitedTaskExecutor {

    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1,
            r -> {
                Thread thread = new Thread(r, "RateLimitedTaskScheduler");
                thread.setDaemon(true); // 设置为守护线程
                return thread;
            });

    private final Semaphore semaphore = new Semaphore(10); // 控制每秒最多 10 个任务执行
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.MILLISECONDS, workQueue,
            r -> {
                Thread thread = new Thread(r, "SingleWorkerThread");
                thread.setDaemon(true); // 设置为守护线程
                return thread;
            });

    public void submit(Runnable task) throws InterruptedException {
        if (semaphore.tryAcquire(1, TimeUnit.SECONDS)) { // 尝试获取许可，超时时间为1秒
            scheduleTask(task);
        } else {
            throw new RejectedExecutionException("无法获取执行许可");
        }
    }

    private void scheduleTask(Runnable task) {
        long delay = System.currentTimeMillis() % 1000; // 计算当前毫秒数到下一秒开始的延迟
        scheduler.schedule(() -> {
            try {
                executor.submit(() -> {
                    try {
                        task.run();
                    } finally {
                        semaphore.release(); // 任务完成后释放许可
                    }
                }).get(); // 等待任务完成
            } catch (Exception e) {
                semaphore.release(); // 如果任务抛出异常也要释放许可
                throw new RuntimeException(e);
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimitedTaskExecutor executor = new RateLimitedTaskExecutor();

        for (int i = 0; i < 30; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("正在执行任务 #" + taskId + "，时间：" + System.currentTimeMillis());
            });
        }

        // 关闭执行器
        executor.shutdown();
    }
}
