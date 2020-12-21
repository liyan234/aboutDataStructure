package 希尔排序;


/**
 * 时间复杂度 最好 O(n) 最坏O(n2) 平均O(n 1.3)
 * 空间复杂度 O(1)
 * 不稳定排序
* */
public class ShellSort {

    public static void commonSort(int[] nums) {
        int gap = nums.length;
        while (gap > 1) {
            helpSort(nums, gap);
            gap = gap / 3;
        }
        helpSort(nums, 1);
    }

    private static void helpSort(int[] nums, int gap) {
        for (int i = 1; i < nums.length; i++) {

            int x = nums[i];
            int c = i - gap;
            for (; c >= 0 && nums[c] > x ; c -= gap) {
                nums[c + gap] = nums[c];
            }
            nums[c + gap] = x;
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
