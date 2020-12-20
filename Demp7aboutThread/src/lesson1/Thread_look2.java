package lesson1;

public class Thread_look2 {

    public static void main(String[] args) {
        Thread t = new Thread( "main的子线程") {
            @Override
            public void run() {
                while(true) {
                   /* try {
                        sleep(20000);
                        System.out.println("xx");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        };
        //线程的启动需要使用start方法
        //告诉系统调度本线程
        t.start();
    }
}
