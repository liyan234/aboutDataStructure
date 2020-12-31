package lesson6.阻塞式队列;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                5, // 核心线程数  --> 正式员工
                10, // 最大线程数 --> 正式员工 + 临时工
                60,  // 超过时间就把不是核心线程的给销毁  解雇临时工
                TimeUnit.SECONDS, // idle线程的空闲时间
                new LinkedBlockingQueue<>(),//阻塞队列 ：任务存放的地方(快递仓库)

                // 线程创建线程时，调用该工厂的方法来创建线程
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {

                        return new Thread(new Runnable() {
                            @Override
                            public void run() {
                                long start = System.currentTimeMillis();
                                r.run();
                                long end = System.currentTimeMillis();
                                System.out.println("任务执行时间：" + (end - start) + "ms");
                            }
                        });
                    }
                },// 创建线程的工厂类 (创建线程的标准) 招聘员工的标准

                new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
                // 达到最大线程数且阻塞队列已满
                /**
                 *  满足拒绝的条件的时候
                 *  拒绝策略 ：
                 *  1. AbortPolicy : 直接抛RejectedExecutionException (不提供handler的默认策略)
                 *  2. CallerRunPolicy : 谁（某个线程）交给我（线程池）任务，（线程池）我拒绝执行，由谁（某个线程）自己执行
                 *  3. DiscardPolicy : 交给我（线程池）的任务直接丢弃掉
                 *  4. DiscardOldestPolicy : 丢弃中阻塞队列中最旧的任务
                 * */
        ); // 创建以后 只要有任务就自动执行

        for (int i = 0; i < 20; i++) {
            // 线程池执行任务： execute, submit  --> 提交执行一个任务
            final int j = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(j);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
