package 进程调度算法;

public class pcbMain {
    public static void main(String[] args) {
        PCB[] pcbs = new PCB[5];
        pcbs[0] = new PCB("P1", 2, 1, 'R');
        pcbs[1] = new PCB("P2", 3, 5, 'R');
        pcbs[2] = new PCB("P3", 1, 3, 'R');
        pcbs[3] = new PCB("P4", 2, 4, 'R');
        pcbs[4] = new PCB("P5", 4, 2, 'R');

        Process process = new Process();

        for (int i = 0; i < pcbs.length; i++) {
            process.addPCB(pcbs[i]);
        }
        process.run();
    }
}
