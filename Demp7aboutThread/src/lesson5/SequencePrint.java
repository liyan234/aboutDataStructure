package lesson5;


/**
 * 每个线程只能答应A 或 B 或 C
 * 三个线程 顺序打印 CBA
 */
public class SequencePrint {
    public static void main(String[] args) {

        Thread C = new Thread(new printTask("C", null));
        Thread B = new Thread(new printTask("B", C));
        Thread A = new Thread(new printTask("A", B));

        A.start();
        B.start();
        C.start();

    }

    public static class printTask implements Runnable {
        private String name;
        private Thread joinTask;

        public printTask(String name) {
            this.name = name;
        }

        public printTask(String name, Thread joinTask) {
            this.name = name;
            this.joinTask = joinTask;
        }

        @Override
        public void run() {
            if (joinTask != null) {
                try {
                    joinTask.join();
                    System.out.println(name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
