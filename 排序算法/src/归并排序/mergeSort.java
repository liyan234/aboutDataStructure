package 归并排序;


/**
 *  时间复杂度 O(nlogn)
 *  空间复杂度 O(n)
 *  稳定排序
* */
public class mergeSort {

    public static void commonSort(int[] nums) {
        helpSort(nums, 0, nums.length);
        // 传进去的是一个左闭右开的数组
    }

    private static void helpSort(int[] nums, int start, int end) {
        if(start >= end - 1) {
            return;
        }
        int mid = (end + start) / 2;

         // 传进去的是一个左闭右开的数组
        helpSort(nums, start, mid);
        helpSort(nums, mid, end);

        merge(nums, start, mid, end);

    }

    // 开始 归并排序
    private static void merge(int[] nums, int start, int mid, int end) {
        int low = start;
        int mid2 = mid;

        int length = end - start;
        int[] array = new int[length];
        int k = 0;

        //
        while (low < mid && mid2 < end) {
            if ( nums[low] <= nums[mid2]) {
                array[k] = nums[low];
                k++;
                low++;
            } else {
                array[k] = nums[mid2];
                mid2++;
                k++;
            }
        }

        while (low < mid) {
            array[k] = nums[low];
            k++;
            low++;
        }

        while (mid2 < end) {
            array[k] = nums[mid2];
            mid2++;
            k++;
        }

        // 将原来的数组写回去
        // 写回去的时候一定加上start  并且长度是小于我们当前新建的归并数据
        for (int i = 0; i < length; i++) {
            nums[i + start] = array[i];
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
