package com.javaops.webapp;

public class DeadLockEx {
    static int counter = 0;
    static final Object OBJECT_1 = new Object();
    static final Object OBJECT_2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            synchronized (OBJECT_1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (OBJECT_2) {
                    func();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (OBJECT_2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (OBJECT_1) {
                    func();
                }
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter);
    }

    public static void func() {
        for (int i = 0; i < 10; i++) {
            counter++;
        }
    }
}
