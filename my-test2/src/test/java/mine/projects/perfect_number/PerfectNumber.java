package mine.projects.perfect_number;

/**
 * @author zero
 * @description 寻找完美数。
 * @date 2025-10-19
 */
public class PerfectNumber {

	public static void main(String[] args) {
		int i = 1;
		int count = 0;
		while (true) {
			if (isPerfectNumber(i)) {
				System.out.println(String.format("%d 是一个完美数。", i));
				count++;
				System.out.println(String.format("当前共有%d个完美数",  count));
			}
			i++;
		}
	}

	public static boolean isPerfectNumber(int number) {
		System.out.println(String.format("开始执行：%d", number));
		int res = 0;
		for (int i = 1; i < number; i++) {
			if (res > number) {
				return false;
			}

			if (number % i == 0) {
				res += i;
			}
		}

		return number == res;
	}

}
