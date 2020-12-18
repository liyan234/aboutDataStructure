package 银行家;

import java.util.Scanner;

public class bankMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("请规定系统有几种资源");

        int num = sc.nextInt();//有几种系统资源 就是 ABCD
        //系统总资源
        Resources[] av = new Resources[num];

        for (int i = 0; i < av.length; i++) {
            System.out.println("第" + (i+1) + "个资源");
            System.out.println("输入这个资源的名称");
            String name = sc.next();
            System.out.println("输入资源的数量");
            int x = sc.nextInt();
            av[i] = new Resources(name, x);//初始化完毕

            System.out.println("-----这是一个分隔符----");
        }

        System.out.println("系统资源初始化完毕");

        System.out.println("-----这是一个分隔符----");

        System.out.println("输入进程的个数");
        int num2 = sc.nextInt();
        PCB[] pcbs = new PCB[num2];

        for (int i = 0; i < num2; i++) {
            System.out.println("第" + (i+1) + "个进程");
            System.out.println("输入这个进程的名称"); // 就是 p0 p1 p2那些
            String name = sc.next();
            System.out.println("输入这个进程的最大需求的资源MAX");

            Resources[] max = new Resources[num];
            int[] maxNum = new int[num];//几种系统资源就需要给多少种东西

            for (int j = 0; j < num; j++) {
                maxNum[j] = sc.nextInt();
            }

            for (int j = 0; j < max.length; j++) {
                max[j] = new Resources(av[j].name, maxNum[j]);
            }


            System.out.println("----输入这个进程已经被分配的资源----");

            Resources[] allocation = new Resources[num];
            int[] allocationResNum = new int[num];
            for (int j = 0; j < num; j++) {
                allocationResNum[j] = sc.nextInt();
            }

            for (int j = 0; j < allocation.length; j++) {
                allocation[j] = new Resources(av[j].name, allocationResNum[j]);
            }
            System.out.println();

            pcbs[i] = new PCB(name, max, allocation);
            System.out.println("第" + (i+1) + "个进程初始化完毕");
        }


        boolean key = true;

        while (key) {
            bank banker = new bank(av, pcbs);
            banker.resAllocation();//算法的实现
            System.out.println();
            //一般如果不安全的话 就需要继续申请资源 然后避免死锁的是实现
            System.out.println("是否需要再次申请资源： 是请输入1 不请输入0");
            int x = sc.nextInt();
            if (x == 0) {
                key = false;
            } else {
                System.out.println("输入需要申请资源的进程名");
                String name = sc.next();
                System.out.println("输入要申请的资源的数量");
                int[] need = new int[num];
                for (int i = 0; i < num; i++) {
                    need[i] = sc.nextInt();
                }
                System.out.println();

                for (PCB p : pcbs
                     ) {
                    p.finish = false;
                    //看一下的name是否相等 相等后才可以分配
                    if (p.name.equals(name)) {
                        for (int i = 0; i < p.allocation.length; i++) {
                            p.allocation[i].num += need[i];
                        }
                    }
                }
                System.out.println("---再次申请资源完毕，开始分配--");
            }
        }
    }
}
