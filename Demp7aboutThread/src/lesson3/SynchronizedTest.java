package lesson3;

public class SynchronizedTest {

    private static int COUNT = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000 ; i++) {
                    synchronized (SynchronizedTest.class) {
                        COUNT++;
                    }
                }
            }
        });
        t1.start();


        Thread t2 = new Thread((new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    COUNT++;
                }
            }
        }));

        t2.start();

        t1.join();
        t2.join();

        System.out.println(COUNT);

    }
}
