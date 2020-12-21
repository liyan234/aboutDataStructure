package 直接插入排序;


/**
 *  时间复杂度  最好O(n) 最坏O(n2)
 *  空间复杂度  O(1)
 *  稳定
 * */
public class InsertSort {


    // 工作原理就是设置有序区间 然后逐个比较
    // 比较之后扩大有序区间
    public static void commonSort(int nums[]) {
        /**
        *  7, 3, 2, 4, 6, 8
         *  第一次循环  x=nums[1]=3  c=0
         *             nums[c]=7 > 3
         *             nums[c+1]=nums[1]=nums[c]=7
         *       for循环结束 c-- c=-1
         *             nums[0]=3
         *       得到 3，7，2，4，6，8
         *  第二次循环  x=nums[2]=2 c=1
         *             nums[c]=7 > 2
        * */
        for (int i = 1; i < nums.length; i++) {
            // 第一次开始的时候设置有序区间
            // 每一次的比较都是让后一个和前面的有序区间顺序比较
            int x = nums[i]; // 有序区间接下来的一个

            int c = i -1; // 有序区间最后一个

            // 从有序区间的最后后面开始比较 如果比有序区间的最后一个大 直接赋值
            // 如果小的话依次进行交换
            for (; c >= 0 && nums[c] > x; c--) {
                nums[c + 1] = nums[c];
            }
            nums[c + 1] = x;
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
