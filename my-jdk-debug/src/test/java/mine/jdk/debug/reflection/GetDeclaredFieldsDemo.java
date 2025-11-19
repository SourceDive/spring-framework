package mine.jdk.debug.reflection;

import mine.jdk.debug.lock.CounterArray;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author zero
 * @description 获取类中声明的 field 数组。
 * @date 2025-11-19
 */
public class GetDeclaredFieldsDemo {
	public static void main(String[] args) {
		Field[] declaredFields = CounterArray.class.getDeclaredFields();
		System.out.println(Arrays.stream(declaredFields).toArray());
	}
}
