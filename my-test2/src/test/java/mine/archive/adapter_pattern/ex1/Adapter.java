package mine.archive.adapter_pattern.ex1;

/**
 * @author zero
 * @description todo
 * @date 2025-07-24
 */
public class Adapter implements ChineseDoc {

	private EnglishManual englishManual;

	public Adapter(EnglishManual englishManual) {
		this.englishManual = englishManual;
	}

	@Override
	public String getContent() {
		String englishText = englishManual.fetchEnglishText();
		return adapt(englishText);
	}

	// 转换逻辑
	private String adapt(String text) {
		// 简化的翻译逻辑（真实场景可能调用AI API）
		if (text.contains("START")) return "点击开始按钮启动系统";
		return "【翻译结果】" + text;
	}
}
