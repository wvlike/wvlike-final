package com.wvlike.user.domain;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Date: 2024/3/7
 * @Author: tuxinwen
 * @Description:
 */
public class FunnelQueue<T> {
    private LinkedBlockingQueue<T> queue;
    private int maxSize;

    public FunnelQueue(int maxSize) {
        this.queue = new LinkedBlockingQueue<>(maxSize);
        this.maxSize = maxSize;
    }

    public void add(T element) {
        if (queue.size() >= maxSize) {
            // Remove the oldest element if the queue is full
            queue.poll();
        }
        queue.offer(element);
    }

    public void process() {
        // Process up to 5 elements at a time
        T element = queue.poll();
        if (element != null) {
            // Perform your desired operation on the element
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
            System.out.println("Processed: " + element);
        }
    }

}
