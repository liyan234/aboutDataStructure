package 快排;

/**
 * 时间复杂度 最坏O(n2) 平均 O(nlogn)
 * 空间复杂度 O(Logn) O(n)
 * 不稳定排序 12344 可能会改变两个数字的相对顺序
 * */

public class QuickSort {

    public static void commonSort(int nums[]) {
        int len = nums.length;
        helpSort(nums, 0, len-1);
    }

    private static void helpSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        // 通过 partition 排序
        // 然后 进行获取partition之后的基准值
        // 这个基准值就是从左边 到右边遍历
        int index = partition(nums, left, right);
        // 每一次的partition遍历 都会分开两个数组
        helpSort(nums, left, index);
        helpSort(nums, index + 1, right);
    }

    private static int partition(int[] nums, int start, int end) {
        int value = nums[start];
        int left = start;
        int right =  end;

        while (left < right) {
            // 必须先从后面遍历比较

            while ( (left < right) && nums[right] >= value) {
                right--;
            }
            // 然后再从前面进行遍历比较
            // 然后才可以让获得的基准值是
            while ( (left < right) && nums[left] <= value) {
                left++;
            }

            // 左右两边 进行交换
            swap(nums, left, right);
        }
        // left 和 right 相遇了
        swap(nums, left, start);
        return left;

    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,8,7,6,4};
        commonSort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }

}
