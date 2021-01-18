package lesson5;

public class SequencePrint3 {

    /**
     * 打印几次
     * */
    private static int COUNT = 10;

    public static void main(String[] args) {

        Thread A = new Thread(new Task('A'));
        Thread B = new Thread(new Task('B'));
        Thread C = new Thread(new Task('C'));

        A.start();
        B.start();
        C.start();

    }
    public static class Task implements Runnable {
        public char name;

        public Task(char name) {
            this.name = name;
        }
        private static char[] CHS = {'A', 'B', 'C'};
        private static int INDEX = 0;
        @Override
        public void run() {
            try {
                for (int i = 0; i < COUNT; i++) {
                    synchronized (CHS) {
                        // while循环使用wait
                        while (CHS[INDEX] != name) {
                            CHS.wait();
                        }
                        System.out.print(name);
                        if (name == 'C') {
                            System.out.println();
                        }
                        INDEX = (INDEX + 1) % CHS.length;
                        CHS.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
