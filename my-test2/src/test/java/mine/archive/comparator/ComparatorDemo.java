package mine.archive.comparator;

import com.ongoing.demo.domain.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author zero
 * @description Comparator按字段排序
 * @date 2025-05-09
 */
public class ComparatorDemo {
	public static void main(String[] args) {
		List<Person> personList = new ArrayList<>();
		Person mike = new Person("mike", 18);
		Person xiao = new Person("xiao", 17);
		personList.add(mike);
		personList.add(xiao);

		personList.sort(Comparator.comparing(p -> p.getAge()));

		Person[] array = personList.toArray(new Person[0]);
		System.out.println(Arrays.stream(array).toArray());

//		Arrays.sort(array, Comparator.comparingInt(Person::getAge));

		System.out.println(Arrays.stream(array).toArray());
	}

}
