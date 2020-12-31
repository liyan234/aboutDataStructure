package lesson5;

/**
 * 生产者消费者问题
 */
public class BoundedBufferProblem {

    private static int PRODUCER_NUMBER = 0;

    // 仓库 最多存储一百个面包
    private static int COUNT = 0;

    public static void main(String[] args) {

        //生产者
        Thread[] producers = new Thread[10];

        for (int i = 0; i < 10; i++) {
            producers[i] = new Thread(new produce(String.valueOf(i)));
        }

        //消费者
        Thread[] consumers = new Thread[20];
        for (int i = 0; i < 20; i++) {
            consumers[i] = new Thread(new consume(String.valueOf(i)));
        }

        for (Thread t : producers) {
            t.start();
        }

        for (Thread t : consumers) {
            t.start();
        }
    }

    // 消费者 一直消费 每次消费一个面包
    public static class consume implements Runnable {

        public String name;

        public consume(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            // 一直消费
            try {
                // 一天最多卖900个
                while (true) {
                    // 保证原子性 加锁操作
                    synchronized (BoundedBufferProblem.class) {
                        // 因为每次加一
                        if (PRODUCER_NUMBER == 900) {
                            break;
                        }
                        // 判断是否满足被消费
                        if (COUNT == 0) {
                            // 释放对象锁 并且等待
                            BoundedBufferProblem.class.wait();
                        } else {
                            System.out.print("仓库中还有" + COUNT + "个面包，消费者" + name + "拿走一个还剩下");
                            //执行消费操作
                            COUNT--;
                            System.out.println(COUNT + "个面包");
                            // 全部唤醒因为wait()方法而阻塞的线程
                            BoundedBufferProblem.class.notifyAll();
                            Thread.sleep(1000);
                        }
                    }
                    // 当前线程休眠100ms
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 生产者
    public static class produce implements Runnable {

        public String name;

        public produce(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            // 生产者一直生产
            try {
                // 最多生产 30 次
                for (int i = 0; i < 30; i++) {
                    synchronized (BoundedBufferProblem.class) {
                        //
                        while (COUNT + 3 > 100) {
                            BoundedBufferProblem.class.wait();
                        }
                        // 开始生产
                        System.out.print("仓库中原有" + COUNT + "个面包，生产者" + name + "生产了3个面包，仓库中现有");
                        COUNT = COUNT + 3;
                        PRODUCER_NUMBER = PRODUCER_NUMBER + 3;
                        System.out.println(COUNT + "个面包");
                        BoundedBufferProblem.class.notifyAll();
                        Thread.sleep(1000);
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
