package one;

public class ThreadDemo {

    public static class MyThread extends Thread{
        @Override
        public void run() {
            //具体的方法实现
        }

    }

    public static void main(String[] args) {

        MyThread t1 = new MyThread();
        t1.start();//调用重写的MyThread中run方法

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
               //方法的实现
            }
        });
        t2.start();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                //方法的实现
            }
        };
        Thread t3 = new Thread(run);
        t3.start();
    }
}
