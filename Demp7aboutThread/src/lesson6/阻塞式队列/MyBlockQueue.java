package lesson6.阻塞式队列;

/**
 * 实现线程安全问题
 * 1. 线程安全问题 ：在多线程下 ，put take 不具有原子性， 4个属性 不具有原子性
 * 2.
 *
 * */


// 使用泛型
public class MyBlockQueue<T> {

    // 数组实现循环队列
    private Object[] queue;

    /**
     * 存放元素的索引
     * */
    private int putIndex;

    /**
     *取元素的索引
     * */
    private int takeIndex;

    /**
     * 当前存放元素的数量
     * */
    private int size;

    public MyBlockQueue(int len) {
        this.queue = new Object[len];
    }

    /**
     * 存放元素
     * 需要考虑 ： putIndex 超过数组的长度
     *            size 达到数组的最大长度
     * */
    // 静态方法 + synchronized 加锁对象是当前 类.class
    // 实例方法 + synchronized 加锁对象是 this(当前对象的引用)
    public synchronized void put (T e) throws InterruptedException {
        // 当阻塞等待到唤醒并再次竞争成功对象锁，恢复后往下执行时，
        // 条件可能被其他对象修改
        while (size == queue.length) {
           this.wait();
        }
        queue[putIndex] = e; // 存放元素
        // 处理数组下标
        putIndex = ( putIndex + 1 ) % queue.length;

        size++;
        this.notifyAll();
    }

    /**
     * 取元素
     * */
    public synchronized T take() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        T t = (T)queue[takeIndex];
        queue[takeIndex] = null;
        takeIndex = (takeIndex + 1) % queue.length;
        size--;
        notifyAll();
        return t;
    }

    /**
     *  size 不加锁
     * */
    public synchronized int size() {
        return size;
    }

    public static void main(String[] args) {
        MyBlockQueue<Integer> queue = new MyBlockQueue<>(10);
        // 多线程调式方式 1. 写打印语句 2.jconsole
        // debug 在多线程的某些场景并不适用
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 1000; j++) {
                            queue.put(j);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            int i = queue.take();
                            System.out.println(Thread.currentThread().getName() + ":" + i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }



}
