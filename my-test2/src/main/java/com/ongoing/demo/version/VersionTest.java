package com.ongoing.demo.version;

import org.springframework.core.SpringVersion;

/**
 * @author zero
 * @description 打印spring版本号
 * @date 2025-04-13
 */
public class VersionTest {
	public static void main(String[] args) {
		System.out.println(SpringVersion.getVersion());
	}
}
