package Lesson2;

public class ThreadTest1 {
    public static void main(String[] args) throws InterruptedException {

       /* // 子线程休眠3s后去执行
        for (int i = 0; i < 20 ; i++) {
            final int k = i;
            // new Thread操作较为耗时
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 匿名内部类使用外部的变量 必须是用final修饰的
                    try {
                        //休眠3s
                        Thread.sleep(3000);
                        System.out.println(k);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        // 主线程和子线程同时执行
        System.out.println("ok");
        */

        /*for (int i = 0; i < 20 ; i++) {
            final int k = 0;
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(k);
                }
            });
            t2.start();
        }
        System.out.println("ok");*/

        // t和main同时并发并行的执行
        // main一直是处于运行态执行代码 所以“主线程”打印的快
        // t是需要执行start转变成就绪态，然后需要系统去调度转变成运行态
        // 申请系统创建t 申请系统执行线程t 创建态转变为就绪态 就绪态转变成运行态需要系统去调度
        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t");
            }
        });
        t.start();
        System.out.println("主线程");*/


        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            final int k = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println(k);
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }

        /*for (Thread t : threads) {
            t.join();
        }*/

        //子线程执行完 主线程执行
        //Debug运行 idea后台不会创建多余的线程
        //run运行 idea后台会创建一个线程
        while (Thread.activeCount() > 2) {
            Thread.yield();// 当前线程让步，从运行态转变成就绪态。
        }
        System.out.println("ok");
    }

}
