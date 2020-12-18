package 银行家;



//系统资源的数据结构
class Resources {
    public String name;
    public int num;

    public Resources(String name, int num) {
        this.name = name;
        this.num = num;
    }

    @Override
    public String toString() {
        return name  + "有" + num + "个资源";
    }
}
//进程的数据结构
class PCB {
    public String name;
    public Resources[] max;//最大需求资源
    public Resources[] allocation;//已分配的资源
    public Resources[] need;//需要的资源
    public boolean finish = false;//表示pcb是否获得足够资源

    public PCB(String name, Resources[] max, Resources[] allocation) {
        this.name = name;
        this.max = max;
        this.allocation = allocation;
        this.need = new Resources[max.length];
        for (int i = 0; i < need.length; i++) {
            this.need[i] = new Resources(max[i].name, max[i].num - allocation[i].num);
        }
    }

    @Override
    public String toString() {
        return this.name +(this.finish ? "进程已得到足够资源" : "需要等待");
    }
}

//银行的数据结构
public class bank {
    //我们当前所需要的资源
    private Resources[] work;

    //所有进程
    private PCB[] pcbs;

    public bank(Resources[] Available, PCB[] pcbs) {
        this.work = Available;
        this.pcbs = pcbs;
        leftOver();
    }


    //计算可以用剩下可以用的资源
    private void leftOver() {
        for (int i = 0; i < this.work.length; i++) {
            //初始资源减去已分配的资源
            this.work[i].num = this.work[i].num - pcbsAllRes(i);
        }
    }

    //计算所有进程已分配的第i个资源总数
    private int pcbsAllRes(int index) {
        int sum = 0;
        for ( PCB p : pcbs
        ) {
            sum += p.allocation[index].num;
        }
        return sum;
    }

    //判断是否为安全状态
    private boolean ifSafe() {
        for (PCB p : pcbs
        ) {
            if (! p.finish) {
                return false;
            }
        }
        return true;
    }

    //进行资源分配
    public void resAllocation() {
        //对进程进行循环资源分配,当所有进程都需要等待或者所有进程都为安全状态退出循环
        for (int i = 0; !ifSafe() && !ifAllNeedWait(); i++) {
            //实现循环
            if (i == this.pcbs.length) {
                i = 0;
            }
            //判断当前这个进程是否已经获得过足够资源
            if (pcbs[i].finish) {
                continue;
            }

            //判断当前这个进程是否需要等待
            if (! needWait(pcbs[i])) {
                //进行资源分配
                mainOperation(pcbs[i]);
                System.out.println(pcbs[i]);
                System.out.println();
                displayWorks();
            }
        }
        System.out.println();
        if (ifSafe()) {
            System.out.println("safe");
        } else {
            System.out.println("unsafe");
        }
    }

    private void displayWorks() {
        System.out.println("此时系统可用资源为:");
        for (Resources r : this.work
        ) {
            System.out.println(r);
        }
        System.out.println("----这是分割符-----");
    }

    private boolean ifAllNeedWait() {
        for (PCB p : this.pcbs
        ) {
            //如果该进程已经得到了足够的资源就不进行判断
            if (!p.finish) {
                //如果该进程不需要等待就直接返回false
                if (!needWait(p)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void mainOperation(PCB pcb) {
        //运行到这说明该进程可以得到足够的资源,那么直接将该进程已分配的资源放回到系统中
        //并将finish改为true
        for (int i = 0; i < this.work.length; i++) {
            this.work[i].num += pcb.allocation[i].num;
            pcb.finish = true;
        }
    }

    private boolean needWait(PCB pcb) {
        //挨个判断此时pcb这个进程所需要的每个资源,如果need大于系统当前可分配资源,就说明需要等待
        for (int i = 0; i < this.work.length; i++) {
            if (this.work[i].num < pcb.need[i].num) {
                return true;
            }
        }
        return false;
    }
}

