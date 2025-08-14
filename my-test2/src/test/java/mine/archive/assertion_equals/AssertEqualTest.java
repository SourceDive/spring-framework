package mine.archive.assertion_equals;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.AssertionErrors;

/**
 * @author zero
 * @description 测试 AssertionErrors.assertEqualsl() 方法。
 * @date 2025-08-14
 */
public class AssertEqualTest {

	@Test
	void testEqualProfiles() {
		AssertionErrors.assertEquals("不一致", "111", "2222");
	}
}
