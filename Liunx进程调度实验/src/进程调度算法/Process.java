package 进程调度算法;

class PCB {
    public String name;
    public PCB next;
    public int time;
    public int priority;
    public char state; //'R'就是就绪状态  'E'为结束状态

    //指针的话 我们在插入的时候就那个是那个的指针了
    public PCB(String name, int time, int priority, char state) {
        this.name = name;
        this.time = time;
        this.priority = priority;
        this.state = state;
    }

    @Override
    public String toString() {
        return "进程名:" + name +  "//需要运行的时间:" + time + "//优先级:" + priority
                + "//状态:" + state;
    }
}


public class Process {
    private PCB head;//规定一个头节点

    //插入操作
    public void addPCB(PCB node) {

        //当一个进程运行完毕的时候也就是time = 0 或者 state = 'E'的时候
        //我们就不需要将这个进程插入到控制块中了
        //直接结束
        if (node.state == 'E') {
            System.out.println(node.name + "运行结束");
            System.out.println(node);
            return;
        }
        //如果所需运行时间为0,状态改为E 退出进程队列 E就是结束
        if (node.time == 0) {
            node.state = 'E';//将状态改为E
            System.out.println(node.name + "运行结束");
            System.out.println(node);
            return;
        }

        //头节点为空的时候 直接插入就好了
        if (this.head == null) {
            this.head = node;
            return;
        }

        //插入的时候需要按优先级进行排序
        //如果这个节点的优先级比头结点大
        //我们需要进行头插法
        if (node.priority > this.head.priority) {
            node.next = this.head;
            this.head = node;
            return;
        }

        //优先级和头结点优先级相同的时候,插到头节点的后面就好了(先来服务)
        if (node.priority == this.head.priority) {
            node.next = this.head.next;
            this.head.next = node;
            return;
        }

        //如果节点的优先级在中间我们需要挨个比较后然后插入
        //我们需要设置两个节点 进行比较后 然后插入
        PCB cur = this.head.next;
        PCB parent = this.head;
        while (cur != null) {
            //当前的节点的优先级小于node的优先级
            if (node.priority > cur.priority) {
                node.next = parent.next;
                parent.next = node;
                return;

            //当前的节点优先级等于node的优先级，同样遵循先来服务
            } else if (node.priority == cur.priority) {
                parent = cur;
                node.next = parent.next;
                parent.next = node;
                return;
            }
            //向后跳一步
            parent = cur;
            cur = cur.next;
        }

        //走到的这里的时候cur已经为空了，那木证明前面的节点的优先级都大于这个节点的优先级了
        //也就是说我们只需要进行尾插就好了
        parent.next = node;
        node.next = null;
    }

    //如何去运行进程
    public void run() {
        while (this.head != null) {
            //我们插入的时候是按照优先级来进行的
            //那木我们的运行的时候就从头节点开始运行
            PCB cur = this.head;//创建一个当前的节点指向头节点
            this.head = this.head.next;
            System.out.println();
            System.out.println("开始执行" + cur.name + "进程");
            System.out.println(cur);
            //当前的节点的时间-1 并且优先级-1
            cur.priority -= 1;
            cur.time -= 1;
            //执行一次后我们需要将这个进程再次插入,不管它的运行时间是否完毕，我们在插入的时候会判断
            System.out.println(cur.name + "进程执行一次完毕");
            //将cur再插入进程队列
            addPCB(cur);

            //当头节点给为null的时候 也就是这个进程控制块中的所有内容已经执行完毕了
            //我们就直接return可以了 不在需要去执行了
            if (this.head == null) {
                System.out.println("进程控制块中的所有进程执行完毕");
                return;
            }
            System.out.println("进程队列的所有进程信息:");
            display();
        }
    }

    //遍历这个队列，我们确定来看看这个队列中每一个pcb的状态
    public void display() {
        for (PCB pcb = this.head; pcb != null; pcb = pcb.next) {
            System.out.println(pcb);
        }
    }
}
