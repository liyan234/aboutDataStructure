package Lesson2;

public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("x");
            }
        });
        t.start();
        t.join();// 当前线程阻塞等待，直到t线程执行完毕，当前线程再往后执行
        // 当前线程 无条件等待t线程执行完毕
        System.out.println("main");*/

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println("t");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
        // t.join(1000);
        // 当前线程限时等待，满足以下任何一个条件就往下等待
        // 等待1000ms或者t线程执行完毕(那个时间短等待多长时间)。
        // 当前线程向下执行。
        t.join(40000);
        // 先等待t执行完毕，系统调度t由就绪态转变为运行态的时间，加上t的时间
        // 等3s打印t，之后打印main
        System.out.println("main");

    }

}
