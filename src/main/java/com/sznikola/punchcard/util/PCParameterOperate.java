package com.sznikola.punchcard.util;

import java.io.*;
import java.util.Properties;

/**
 * @author yzh
 * @Description 配置文件
 * @email yzhcherish@163.com
 * @data 2022-09-08  14:18
 */
public class PCParameterOperate {
    public static String extract(String key) {
        String str = null;
        try {
            Properties prop = new Properties();
            System.out.println("我走了这里");
            InputStream in = new BufferedInputStream(
                    new FileInputStream("./res/punchcard.properties"));    //配置文件路径
            prop.load(in);  //加载配置文件
            str = prop.getProperty(key);    //http://192.168.1.254:8888/common/puncdoutlineservice/userJudge

        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 重指定配置文件中获取key对应的value
     * @param key  配置文件中的key
     * @param url  配置文件的地址
     * @return
     */
    public static String extract(String key,String url) {
        String str = null;
        try {
            Properties prop = new Properties();
            InputStream in = new BufferedInputStream(
                    new FileInputStream(url));
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
}
