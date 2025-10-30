package mine.jdk.debug.collection.map;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zero
 * @description 测试 computeIfAbsent
 * @date 2025-10-30
 */
public class ComputeIfAbsentDemo {
	public static void main(String[] args) {
//        demoCacheStringLength();
//        demoAppendToList();
		myMethod();
	}

	// 示例1：将 computeIfAbsent 用作简单缓存（只计算一次）
	private static void demoCacheStringLength() {
		java.util.Map<String, Integer> nameLengthCache = new java.util.HashMap<>();

		String name = "Alice";
		Integer len1 = nameLengthCache.computeIfAbsent(name, k -> {
			System.out.println("计算一次: " + k);
			return k.length();
		});

		// 第二次对同一个 key 调用时，不会再触发计算函数
		Integer len2 = nameLengthCache.computeIfAbsent(name, k -> {
			System.out.println("不会再计算: " + k);
			return k.length();
		});

		System.out.println("len1=" + len1 + ", len2=" + len2);
		System.out.println("缓存内容: " + nameLengthCache);
		System.out.println("----");
	}

	// 示例2：常见写法——获取（或创建）List 后直接追加元素
	private static void demoAppendToList() {
		java.util.Map<String, java.util.List<String>> tagToItems = new java.util.HashMap<>();

		tagToItems.computeIfAbsent("fruit", k -> new java.util.ArrayList<>()).add("apple");
		tagToItems.computeIfAbsent("fruit", k -> new java.util.ArrayList<>()).add("banana");
		tagToItems.computeIfAbsent("book", k -> new java.util.ArrayList<>()).add("ddd");

		System.out.println(tagToItems);
		System.out.println("----");
	}

	/**
	 * 无则创建集合。
	 */
	private static void myMethod() {
		ConcurrentHashMap<String, Set<String>> setMap = new ConcurrentHashMap<>();

		System.out.println(setMap);
		setMap.computeIfAbsent("key1", key -> new HashSet<>());
		System.out.println(setMap);


		Set<String> set1 = setMap.computeIfAbsent("key1",
				key -> new HashSet<>());
		set1.add("apple");
		System.out.println(setMap);
	}
}
