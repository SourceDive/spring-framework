package mine.archive.adapter_pattern;

/**
 * @author zero
 * @description 适配器模式简单例子
 * @date 2025-07-24
 */
public class ChineseUser {
	public static void main(String[] args) {
		EnglishManual englishManual = new EnglishManual();
		System.out.println(englishManual.fetchEnglishText());
		ChineseDoc chineseDoc = new Adapter(englishManual);
		System.out.println(chineseDoc.getContent());
	}
}
