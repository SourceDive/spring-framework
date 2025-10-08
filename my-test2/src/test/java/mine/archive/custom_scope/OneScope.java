package mine.archive.custom_scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class OneScope implements Scope {

    private int index = 0;

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        System.out.println("get被调用");
        return new Student("skywalker-" + (index++), index);
    }

	@Override
	public Object remove(String name) {
		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {

	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return "";
	}
}