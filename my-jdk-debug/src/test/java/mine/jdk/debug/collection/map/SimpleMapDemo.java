package mine.jdk.debug.collection.map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zero
 * @description 测试 map.putIfAbsent
 * @date 2025-10-28
 */
public class SimpleMapDemo {
	public static void main(String[] args) {

		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
		map.put("one", 1);

		System.out.println(map);

		Integer res = map.putIfAbsent("one", 2);

		System.out.println(map);
		System.out.println(res);

		Integer two = map.put("two", 2);
		System.out.println(map);
		System.out.println(two);
	}

}
