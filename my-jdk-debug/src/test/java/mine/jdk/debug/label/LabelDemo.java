package mine.jdk.debug.label;

/**
 * @author zero
 * @description java label 语法。
 * @date 2025-11-12
 */
public class LabelDemo {
	public static void main(String[] args) {
//		demo_label:
		for (int i = 0; i < 10; i++) {
			for (int i1 = 0; i1 < 10; i1++) {
				System.out.println(i1);
				if (i1 % 2 == 0) {
//					break demo_label;
					break;
				}
			}
		}
	}
}
