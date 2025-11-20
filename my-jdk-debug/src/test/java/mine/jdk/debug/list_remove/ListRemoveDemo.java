package mine.jdk.debug.list_remove;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zero
 * @description 查看 List.remove 的返回值: 为被移除的元素。
 * @date 2025-07-23
 */
public class ListRemoveDemo {
	public static void main(String[] args) {
		List<String> stringList = new ArrayList<String>();
		stringList.add("a");
		stringList.add("b");
		stringList.add("c");
		stringList.add("d");

		String remove = stringList.remove(0);
		System.out.println(remove);
	}
}
