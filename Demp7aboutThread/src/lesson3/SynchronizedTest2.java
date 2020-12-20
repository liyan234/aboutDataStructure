package lesson3;

public class SynchronizedTest2 {
    // 一个教室 座位有50个 座位编号 0 ~ 49
    // 同时有3个老师安排同学的座位
    // 每一个老师安排100个同学
    // 同学可以循环安排 座位安排满结束线程
    private static int SEATS = 50;

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[3];

        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        synchronized (SynchronizedTest2.class) {
                            if (SEATS == 0) {
                                return;
                            } else {
                                SEATS--;
                                System.out.println(Thread.currentThread().getName() + " " + SEATS + " " + j);
                            }
                        }
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }

        for (Thread t :
                threads) {
            t.start();
        }

        for (Thread t :
                threads) {
            t.join();
        }
        System.out.println(SEATS);

    }
}
