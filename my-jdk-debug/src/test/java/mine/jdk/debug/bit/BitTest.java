package mine.jdk.debug.bit;

/**
 * @author zero
 * @description 测试位运算。
 * @date 2025-12-12
 */
public class BitTest {
	public static void main(String[] args) {
//		int n = 2 * 2 * 2;
//		System.out.println(n);
//		System.out.println(n & (n-1));

		System.out.println(Integer.numberOfLeadingZeros(0));

		System.out.println(String.format("%32s", Integer.toBinaryString(5)).replace(' ', '0'));
	}
}
