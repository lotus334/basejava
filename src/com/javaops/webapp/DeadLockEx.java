package com.javaops.webapp;

public class DeadLockEx {
    static final Object OBJECT_1 = new Object();
    static final Object OBJECT_2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new RunnableImpl(OBJECT_2, OBJECT_1);
        Thread thread2 = new RunnableImpl(OBJECT_1, OBJECT_2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(RunnableImpl.counter);
    }

}

class RunnableImpl extends Thread {
    static int counter = 0;

    Object lock1;
    Object lock2;

    public RunnableImpl(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (lock1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                func();
            }
        }
    }

    private void func() {
        for (int i = 0; i < 10; i++) {
            counter++;
        }
    }
}