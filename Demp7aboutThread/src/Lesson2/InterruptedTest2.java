package Lesson2;

public class InterruptedTest2 {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    // Thread.interrupted() 返回当前线程的中断标志位，然后重置中断标志位
                    System.out.println(i + " " +  Thread.interrupted());
                }
            }
        });
        t.start();
        System.out.println("t.start");
        t.interrupt();
        System.out.println("t.end");
    }

}
