package lesson1;

public class Thread_look3 {
    public static void main(String[] args) {
        // 主线程和其他线程 并发和并行都有

        //创建一个线程对象，匿名内部类重写了Thread的run方法
        Thread t = new Thread() {
            @Override
            public void run() {
                // 线程进入运行态之后执行
                while (true) {
                    System.out.println(1);
                }
            }
        };
        // 告诉系统申请调度了 但是不知道几时会调度这个线程
        t.start();

        while (true) {
            // main线程所做的事情
            System.out.println(2);
        }
    }
}
