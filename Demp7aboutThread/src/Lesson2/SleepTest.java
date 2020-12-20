package Lesson2;

public class SleepTest {
    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        Thread.sleep(999999);
        // TIMED_WAITING 休眠状态
        // RUNNABLE  可执行状态 (就绪状态和运行状态的结合体)
        // RUNNING 运行状态

    }
}
