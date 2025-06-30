package mine.projects.objenesis;

/**
 * @author zero
 * @description todo
 * @date 2025-06-17
 */
public class Demo {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		TestObj testObj = TestObj.class.newInstance();
		System.out.println(testObj);
	}
}
