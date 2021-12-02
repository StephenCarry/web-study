package com.yaoo;

public class Sem {
    private int num = 0;

    public Sem(int num) {
        if (num > 0) {
            this.num = num;
        }
    }

    public synchronized void P() throws InterruptedException {
        while (num == 0) {
            wait();
        }
        num--;
    }

    public synchronized void V() {
        num++;
        notifyAll();
    }
}
