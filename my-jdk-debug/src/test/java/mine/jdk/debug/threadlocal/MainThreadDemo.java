package mine.jdk.debug.threadlocal;

/**
 * 这个程序值得看看。
 *
 * main线程不会得到异步线程的本地变量。
 */
public class MainThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        thread.start();

		Thread.sleep(2000);
        System.out.println(thread.get());
    }
}

class MyThread extends Thread {
    private ThreadLocal<String> value = new ThreadLocal<>();
    public String get() {
        return value.get();
    }
    @Override
    public void run() {
        value.set("hello");

		System.out.println(Thread.currentThread().getName() + ":" + value.get());
    }
}