package test;

/**
 * 冒泡排序
 * 冒泡排序的时间复杂度：O(n的2次幂)、
 * 空间复杂度是不变的： O(1)  1.参数为数组时，则只需要为它分配一个存储由实参传送来的一个地址指针的空间，即一个机器字长空间
 *                    2.若形参为引用方式，则也只需要为其分配存储一个地址的空间，用它来存储对应实参变量的地址，以便由系统自动引用实参变量。 
 * @author xianjiao.luo
 *
 */
public class BubbleSort {

	public static int[] bubbleSort(int a[]) {
		
		for(int i=0; i<a.length-1; i++) {   //最多做n-1 次
			for(int j=0; j<a.length-i-1; j++) {  //对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
				if(a[j] < a[j+1]) {  //大到小排序
					int temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
				}		
			}
			System.out.println("第" + (i+1) + "次排序结果：");
			for(int n=0; n<a.length; n++) {
				System.out.print(a[n] + "\t");
			}
			System.out.println("");
		}
		System.out.println("最终排序结果：");
		for(int n=0; n<a.length; n++) {
			System.out.print(a[n]+"\t");
		}		
		return a;
	}
	
	public static void main(String[] args) {
		int[] a =  {1,3,2,9,6,8,7,11,19,18};
//		Integer[] b = new Integer[] {1,6,5,4,10};
		bubbleSort(a);
	}
}
