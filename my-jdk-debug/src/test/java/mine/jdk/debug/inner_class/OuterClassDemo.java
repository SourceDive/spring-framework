package mine.jdk.debug.inner_class;

/**
 * @author zero
 * @description 演示内部类隐式持有外部类引用的机制
 * @date 2025-11-05
 */
public class OuterClassDemo {
	private String name;
	private int value = 100;

	/**
	 * 内部类：隐式持有外部类 OuterClassDemo 的引用
	 * 这个引用可以通过 OuterClassDemo.this 显式访问
	 */
	class InnerClass {
		/**
		 * 内部类可以直接访问外部类的私有字段
		 * 这是因为内部类隐式持有了外部类的引用
		 */
		public void accessOuterFields() {
			// 方式1：直接访问外部类字段（隐式使用 OuterClassDemo.this）
			System.out.println("直接访问外部类字段 name: " + name);
			System.out.println("直接访问外部类字段 value: " + value);
			
			// 方式2：显式使用 OuterClassDemo.this 访问外部类引用
			System.out.println("外部类引用: " + OuterClassDemo.this);
			System.out.println("通过显式引用访问 name: " + OuterClassDemo.this.name);
			System.out.println("通过显式引用访问 value: " + OuterClassDemo.this.value);
		}
		
		/**
		 * 在 lambda 表达式中访问外部类字段
		 * lambda 也会持有外部类的引用
		 */
		Runnable task = () -> {
			System.out.println("Lambda 中访问外部类字段 name: " + name);
			System.out.println("Lambda 中访问外部类引用: " + OuterClassDemo.this);
		};
		
		/**
		 * 获取外部类引用
		 * 用于验证内部类确实持有外部类引用
		 */
		public OuterClassDemo getOuterInstance() {
			return OuterClassDemo.this;
		}
		
		/**
		 * 修改外部类字段
		 * 证明内部类可以通过隐式引用修改外部类状态
		 */
		public void modifyOuterField() {
			OuterClassDemo.this.name = "被内部类修改";
			OuterClassDemo.this.value = 200;
		}
	}

	public static void main(String[] args) {
		System.out.println("========== 演示内部类隐式持有外部类引用 ==========\n");
		
		// 创建外部类实例
		OuterClassDemo outer1 = new OuterClassDemo();
		outer1.name = "外部类实例1";
		outer1.value = 100;
		
		// 创建外部类实例2
		OuterClassDemo outer2 = new OuterClassDemo();
		outer2.name = "外部类实例2";
		outer2.value = 200;
		
		System.out.println("1. 创建外部类实例:");
		System.out.println("   outer1: " + outer1);
		System.out.println("   outer2: " + outer2);
		
		// 从 outer1 创建内部类实例
		OuterClassDemo.InnerClass inner1 = outer1.new InnerClass();
		// 从 outer2 创建内部类实例
		OuterClassDemo.InnerClass inner2 = outer2.new InnerClass();
		
		System.out.println("\n2. 从不同外部类实例创建内部类:");
		System.out.println("   inner1: " + inner1);
		System.out.println("   inner2: " + inner2);
		
		System.out.println("\n3. 验证内部类持有的外部类引用:");
		System.out.println("   inner1 持有的外部类引用: " + inner1.getOuterInstance());
		System.out.println("   inner2 持有的外部类引用: " + inner2.getOuterInstance());
		System.out.println("   inner1 的引用 == outer1? " + (inner1.getOuterInstance() == outer1));
		System.out.println("   inner2 的引用 == outer2? " + (inner2.getOuterInstance() == outer2));
		
		System.out.println("\n4. 内部类访问外部类字段（隐式引用）:");
		inner1.accessOuterFields();
		
		System.out.println("\n5. Lambda 表达式中的外部类引用:");
		inner1.task.run();
		
		System.out.println("\n6. 内部类修改外部类字段（通过隐式引用）:");
		System.out.println("   修改前 - outer1.name: " + outer1.name + ", outer1.value: " + outer1.value);
		inner1.modifyOuterField();
		System.out.println("   修改后 - outer1.name: " + outer1.name + ", outer1.value: " + outer1.value);
		
		System.out.println("\n7. 验证不同内部类实例持有不同的外部类引用:");
		System.out.println("   inner1 访问的 name: " + inner1.getOuterInstance().name);
		System.out.println("   inner2 访问的 name: " + inner2.getOuterInstance().name);
		
		System.out.println("\n========== 总结 ==========");
		System.out.println("内部类隐式持有外部类引用的关键点：");
		System.out.println("1. 内部类在创建时自动持有其外部类实例的引用");
		System.out.println("2. 内部类可以直接访问外部类的所有成员（包括私有成员）");
		System.out.println("3. 通过 OuterClassDemo.this 可以显式访问外部类引用");
		System.out.println("4. 每个内部类实例都绑定到一个特定的外部类实例");
	}
}
