package 选择排序;

/**
 * 时间复杂度 O(n2)
 * 空间复杂度 O(1)
 * 不稳定排序
 * */
public class SelectSort {

    /**
     *  第一个数 和 后面的依次比较 选择出最小的数
     *  然后最小的数和第一个数交换
     *  然后开始第二个数和后面的数开始比较
     * */

    public static void commonSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {

            int max = i;
            // int min = i;
            for (int j = i + 1; j < nums.length; j++) {
            //    if(nums[j] < nums[min]) {
            //        min = j;
            //    }

                if (nums[j] > nums[max]) {
                    max = j;
                }
            }
            if(max != i) {
                int tmp = nums[i];
                nums[i] = nums[max];
                nums[max] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,8,7,6,4};
        commonSort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
