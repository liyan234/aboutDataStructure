package 堆排序;

public class HeapSort {
    public static void commonSort(int[] nums) {
        // 1. 建堆
        createHeap(nums);
        // 2. 调整堆
        int size = nums.length - 1;

        for (int i = 0; i < nums.length; i++) {
            int tmp = nums[size];
            nums[size] = nums[0];
            nums[0] = tmp;

            size--;

            shiftDown(nums, size, 0);
        }

    }

    private static void createHeap(int[] nums) {
        // 最后一个非叶子结点开始建堆
        for (int i = nums.length/2 - 1; i >= 0; i--) {
            shiftDown(nums, nums.length, i);
        }
    }

    private static void shiftDown(int[] nums, int length, int i) {
        // 父亲结点
        int parent = i;
        // 获取child
        int child = (parent * 2) + 1;

        // 循环向下调整
        while (child < length) {
            // 判断那个更小 与更小的交换
            if (child + 1 < nums.length && nums[child + 1] < nums[child]) {
                child = child + 1;
            }
            // 将小的向上调整
            // 大的向下调整
            if(nums[child] < nums[parent]) {
                int tmp = nums[child];
                nums[child] = nums[parent];
                nums[parent] = tmp;
            } else {
                break;
            }
            parent = child;
            child = (parent * 2) + 1;
        }
    }

    public static void main(String[] args) {
        int[] nums = {7,3,4,1,5,8,9};
        commonSort(nums);

        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
