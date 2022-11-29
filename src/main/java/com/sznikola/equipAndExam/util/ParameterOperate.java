package com.sznikola.equipAndExam.util;

import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

@Slf4j
public class ParameterOperate {

	private static final String FILE_PATH =  "./res/equipAndExam.properties";

	private static final File file = new File(FILE_PATH);

	private static Properties properties = new Properties();

	static {
		try {
			properties.load(Files.newInputStream(file.toPath()));
		} catch (IOException e) {
			log.info("{}加载失败, 未找到配置文件", FILE_PATH);
			throw new RuntimeException(e);
		}
	}

	@SneakyThrows(Exception.class)
	private static void reload(){
		properties.load(Files.newInputStream(file.toPath()));
	}
	public static String extract(String key) {
		return properties.getProperty(key);

	}

	public static boolean inputValue(String key, String value) {

		File file = new File(FILE_PATH);
		try (InputStream fis = Files.newInputStream(file.toPath());

			OutputStreamWriter osw = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)){

			properties.setProperty(key, value);

			properties.store(osw, "服务器相关信息");

		}catch (Exception e){
			log.info("写入配置文件失败", e);
			return false;
		}
		return true;
	}

	public static boolean store(String key, String value, String comment){
		File file = new File(FILE_PATH);
		try (InputStream fis = Files.newInputStream(file.toPath());
			 OutputStreamWriter osw = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)){

			properties.setProperty(key, value);

			Enumeration<Object> keys = properties.keys();

			StringBuilder sb = new StringBuilder();

			sb.append("# ").append(UnicodeUtil.toUnicode(comment)).append("\n");
			sb.append("# ").append(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")).append("\n");

			while (keys.hasMoreElements()) {
				String k = (String)keys.nextElement();
				String v = properties.getProperty(k);
				sb.append(k).append("=").append(v).append("\n");
			}

			sb.substring(0, sb.toString().lastIndexOf("\n"));

			osw.write(sb.toString());
			osw.flush();

			// 重新加载配置文件
			reload();
		}catch (Exception e){
			log.info("写入配置文件失败", e);
			return false;
		}

		return true;
	}
}
