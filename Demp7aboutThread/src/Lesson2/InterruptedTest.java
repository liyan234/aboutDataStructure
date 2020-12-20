package Lesson2;

public class InterruptedTest {

    public static void main(String[] args) throws InterruptedException {
        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 获取当前线程的标志位
                    for (int i = 0; i < 1000 && !Thread.currentThread().isInterrupted(); i++) {
                        System.out.println(i);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    // 捕获异常 之后 就会重置t线程的中断标志位
                    e.printStackTrace();
                }
            }
        });

        t.start(); //线程启动 标志位为 false
        System.out.println("t.start");
        Thread.sleep(5000);
        t.interrupt(); // 告诉t线程要中断，由t的代码自行决定是否要中断
        // 即使t线程处于阻塞状态 也会以抛异常的方式中断线程
        // 线程中有一个标志位
        // 实现就是设置标志位为true
        System.out.println("t.stop");*/


    }
}
