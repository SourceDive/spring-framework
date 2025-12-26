package mine.jdk.debug.spi;

import mine.jdk.debug.spi.spi.Fruit;

import java.util.ServiceLoader;

/**
 * @author zero
 * @description 测试简单的spi案例 根据接口寻找子类
 * @date 2025-12-26
 */
public class LoadDemo {
	public static void main(String[] args) {
		ServiceLoader<Fruit> loader = ServiceLoader.load(Fruit.class);

		loader.forEach((fruit -> System.out.println(fruit.gatName())));
	}
}
