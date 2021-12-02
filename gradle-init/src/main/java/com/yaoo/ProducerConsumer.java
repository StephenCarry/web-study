package com.yaoo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProducerConsumer {
    public static void main(String[] args) {
        Sem empty = new Sem(10);
        Sem full = new Sem(0);
        Sem lock = new Sem(1);
        Queue<String> queue = new LinkedList<>();

        List<Thread> threadPool = new ArrayList<>();
        for (int i=0;i<10;i++) {
            ProducerConsumer pc = new ProducerConsumer(empty,full,lock,queue);
            Thread c = new Thread(pc::consumer);
            threadPool.add(c);
        }
        for (int i=0;i<5;i++) {
            ProducerConsumer pc = new ProducerConsumer(empty,full,lock,queue);
            Thread p = new Thread(pc::producer);
            threadPool.add(p);
        }

        try {
            threadPool.parallelStream().forEach(Thread::start);
            Thread.sleep(5000);
            threadPool.parallelStream().forEach(Thread::interrupt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final Sem empty;

    private final Sem full;

    private final Sem lock;

    private final Queue<String> queue;

    public ProducerConsumer(Sem empty, Sem full, Sem lock, Queue<String> queue) {
        this.empty = empty;
        this.full = full;
        this.lock = lock;
        this.queue = queue;
    }

    public void producer() {
        try {
            empty.P();
            lock.P();
            String s = "object-"+Math.random();
            queue.add(s);
            System.out.println("add "+s);
            lock.V();
            full.V();
        } catch (InterruptedException e) {
            System.out.println("thread interrupt");
        }
    }

    public void consumer() {
        try {
            full.P();
            lock.P();
            System.out.println("get "+queue.remove());
            lock.V();
            empty.V();
        } catch (InterruptedException e) {
            System.out.println("thread interrupt");
        }
    }
}
