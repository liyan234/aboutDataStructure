package Lesson2;



/**
 * 使用 interrupt()之后 代码决定线程是否结束
 * */
public class MyInterruptedTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000 && !Thread.currentThread().isInterrupted() ;i++) {

                    System.out.println(i);
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();

        System.out.println("xxx");
        Thread.sleep(1);
        t.interrupt();
        System.out.println("yyy");

    }

}
