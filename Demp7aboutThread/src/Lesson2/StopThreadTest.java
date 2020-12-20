package Lesson2;

public class StopThreadTest {

    private static volatile boolean STOP = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // .. 执行任务，执行时间较长
                for (int i = 0; i < 1000 && !STOP; i++) {
                    try {
                        System.out.println(i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        System.out.println("t.start");
        // t中断
        // 模拟执行五秒后，还没有结束，要中断，停止t线程
        Thread.sleep(5000);
        STOP = true;
        System.out.println("t.stop");
    }
}
