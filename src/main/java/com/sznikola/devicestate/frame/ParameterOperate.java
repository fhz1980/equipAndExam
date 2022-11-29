package com.sznikola.devicestate.frame;

import java.io.*;
import java.util.Properties;

public class ParameterOperate {
	public static String extract(String key) {
		String str = null;
		try {
			Properties prop = new Properties();
			InputStream in = new BufferedInputStream(
					new FileInputStream("./res/equipAndExam.properties"));
			prop.load(in);
			str = prop.getProperty(key);

		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public static String extractX(String key) throws IOException {
		String str = null;

		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream("./res/equipAndExam.properties"));
		prop.load(in);
		str = prop.getProperty(key);

		return str;
	}

}
