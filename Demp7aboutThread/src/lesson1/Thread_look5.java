package lesson1;

class MyThread extends Thread {
    @Override
    public void run() {

    }
}


class MyRunnable implements Runnable{
    @Override
    public void run() {

    }
}

public class Thread_look5 {
    public static void main(String[] args) {
        Thread t = new Thread("rrr") {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    //某个线程抛异常的时候并不会影响其他线程
                    //如果抛到run方法，这个线程就会停止
                    //线程中处理异常的方法
                    // 1. t.setUncaughtExceptionHandler();
                    // 2. 自己在run()中捕获并处理
                    if (i == 20) {
                        throw new RuntimeException();
                    }
                    System.out.println(i);

                }
            }
        };
        t.start();


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }, "");

        //和上面本质上是一样的
        Thread t3 = new Thread(new MyRunnable());

        Thread t4 = new MyThread();

        // 创建一个线程并且启动
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }, "xxx").start();


        // lambda表达式
        new Thread(() -> new Runnable() {
            @Override
            public void run() {

            }
        }, "");

       // native JNI
    }
}
