package lesson5;


/**
 * 每个线程只能答应A 或 B 或 C
 * 三个线程 顺序打印 ABC
 * 打印十次
 */
public class SequencePrint2 {


    public static void main(String[] args) throws InterruptedException {
        Object x = new Object();
        Object y = new Object();
        Object z = new Object();

        Thread A = new Thread(new printTask("A", x, y));
        Thread B = new Thread(new printTask("B", y, z));
        Thread C = new Thread(new printTask("C", z, x));

        A.start();
        Thread.sleep(10);
        B.start();
        Thread.sleep(10);
        C.start();
    }

    public static class printTask implements Runnable {

        public String name ;
        public Object prev;
        public Object self;

        // 获取两个锁
        public printTask(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 3;
            while (count > 0) {
                synchronized (prev) {
                    synchronized (self) {
                        System.out.println(name);
                        count--;
                        self.notifyAll();
                    }

                    if (count == 0) {
                        prev.notifyAll();
                    } else {
                        try {
                            prev.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }
}
