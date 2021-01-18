package lesson8;

public class ProduceAndConsume {

    /**
     * 仓库现有总量
     * */
    public static int COUNT = 0;

    /**
     * 仓库最大容量
     */
    public static int MAXCOUNT = 600;

    /**
     * 几个生产者
     */
    public static int PRODUCERS = 10;

    /**
     * 生产者每次生产几件
     */
    public static int PRODUCENUM = 3;

    /**
     * 生产者最多生产几次
     */
    public static int PRODUCETEST = 30;


    /**
     * 几个消费者
     */
    public static int CONSUMER = 20;

    /**
     * 消费者每次消费多少
     */
    public static int CONSUMENUM = 1;


    /**
     * 生产者 生产
     */
    public static class produce implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < PRODUCETEST; i++) {
                // 加锁 保证原子性
                try {
                    synchronized (ProduceAndConsume.class) {
                        while (COUNT + PRODUCENUM > MAXCOUNT) {
                            ProduceAndConsume.class.wait();
                        }
                        System.out.print("仓库有" + COUNT + "件," + "生产者生产了" + PRODUCENUM + "件");
                        COUNT += PRODUCENUM;
                        System.out.println("仓库现有" + COUNT + "件");
                        ProduceAndConsume.class.notifyAll();
                        Thread.sleep(100);
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 消费者消费
     */
    public static class consume implements Runnable {

        @Override
        public void run() {
            // 消费者只要有就消费
            while (true) {
                try {
                    synchronized (ProduceAndConsume.class) {
                        while (COUNT == 0) {
                            ProduceAndConsume.class.wait();
                        }
                        System.out.print("仓库有" + COUNT + "件," + "消费者了" + CONSUMENUM + "件");
                        COUNT -= CONSUMENUM;
                        System.out.println("仓库现有" + COUNT + "件");
                        ProduceAndConsume.class.notifyAll();
                        Thread.sleep(100);
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        Thread[] cs = new Thread[PRODUCERS];
        for (int i = 0; i < PRODUCERS; i++) {
            cs[i] = new Thread(new produce());
        }
        for (Thread t: cs) {
            t.start();
        }

        Thread[] pd = new Thread[CONSUMER];
        for (int i = 0; i < CONSUMER; i++) {
            pd[i] = new Thread(new consume());
        }
        for (Thread t: pd) {
            t.start();
        }
    }

}
