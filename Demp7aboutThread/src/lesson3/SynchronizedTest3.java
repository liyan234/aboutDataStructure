package lesson3;

public class SynchronizedTest3 {

    // volatile  保证内存可见性
    private static volatile int SEATS = 50;

    public static void main(String[] args) {
        new Thread(new Task(20)).start();
        new Thread(new Task(20)).start();
        new Thread(new Task(10)).start();

    }

    private static class Task implements Runnable {

        public int num;

        public Task(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                synchronized (Task.class) {
                    if (SEATS > 0 && num > 0) {
                        SEATS--;
                        num--;
                        System.out.println(Thread.currentThread().getName() + " SEATS:" + SEATS + " num:" + num);
                    } else {
                        return;
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
