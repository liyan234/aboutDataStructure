package lesson1;

public class Thread_look6 {

    public static void main(String[] args) {
        Thread t = new Thread("xxx") {
            @Override
            public void run() {
                super.run();
            }
        };

        t.start();



        System.out.println(t.getId());
        System.out.println(t.getName());
        System.out.println(t.getPriority());//优先级
        System.out.println(t.getState());
        System.out.println(t.isAlive());//是否被终结
        System.out.println(t.isDaemon()); //是否是守护线程
        System.out.println(t.isInterrupted()); //
    }
}
