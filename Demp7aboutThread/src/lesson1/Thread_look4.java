package lesson1;

public class Thread_look4 {

    public static void main(String[] args) {
        // 同时启动二十个线程 ，每个线程从 0 以+1的方法加到9999
        // 打印出9999 结束
        for (int i = 0; i < 20; i++) {

            Thread t = new Thread() {
                //线程私有 互不相干
                @Override
                public void run() {
                    // 在多线程的环境下，即使同一个代码块，也可以并发并行代码
                    for (int i = 0; i < 10000; i++) {
                        if (i == 9999) {
                            System.out.println(9999);
                        }
                    }
                }
            };
            t.start();
        }
    }
}
