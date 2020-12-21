package 冒泡排序;


/**
 * 时间复杂度 ： O(n2)
 * 空间复杂度 ： O(1)
 *  稳定排序
 * */
public class BubbleSort {

    public static void commonSort(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = nums.length-1; j > i; j--) {
                //前者大于后者
                if (nums[j] >= nums[j-1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j-1];
                    nums[j-1] = tmp;
                }
            }
        }

    }

    // 优化
    public static void goodSort(int[] nums) {

        Boolean flag = true;
        for (int i = 0; i < nums.length && flag; i++) {
            flag = false;
            for (int j = nums.length-1; j > i; j--) {
                //前者大于后者
                if (nums[j] >= nums[j-1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j-1];
                    nums[j-1] = tmp;
                    flag = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,8,7,6,4};
        commonSort(nums);
        //goodSort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }


}
