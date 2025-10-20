package mine.archive.perfect_number;

/**
 * @author zero
 * @description 寻找完美数。
 * @date 2025-10-19
 */
public class PerfectNumber {

	public static void main(String[] args) {
		int i = 6; // 从 6 开始，因为 6 是最小的完美数
		int count = 0;
		
		System.out.println("开始搜索完美数...");
		
		while (true) {
			if (isPerfectNumber(i)) {
				count++;
				System.out.println(String.format("第%d个完美数: %d", count, i));
			}
			
			// 智能跳过策略：优先检查 2 的幂次相关区域
			// 已知完美数模式，可以跳过一些明显不可能的区间
			if (i < 100) {
				i++; // 小数字逐个检查
			} else if (i < 1000) {
				i += 2; // 跳过奇数，因为除了 6 外，其他完美数都是偶数
			} else {
				i += 4; // 更大的数字，使用更大的步长
			}
		}
	}

	public static boolean isPerfectNumber(int number) {
		// 跳过 1，因为 1 不是完美数
		if (number <= 1) {
			return false;
		}
		
		int sum = 1; // 1 是所有大于 1 的数的因数
		int sqrt = (int) Math.sqrt(number);
		
		// 只检查到 √number，时间复杂度从 O(n) 降到 O(√n)
		for (int i = 2; i <= sqrt; i++) {
			if (number % i == 0) {
				sum += i; // 加上因数 i
				if (i != number / i) { // 避免重复加完全平方数的平方根
					sum += number / i; // 加上对应的因数 number/i
				}
			}
			
			// 早期终止：如果已经超过目标数，直接返回 false
			if (sum > number) {
				return false;
			}
		}
		
		return number == sum;
	}

}
