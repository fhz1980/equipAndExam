package com.sznikola.devicestate.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-09-09  11:33
 */
public class HttpService {

    public static String faceFea(String url, BufferedImage bufferedImage){
        String result=null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);  //将url的值赋值给httppost
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
            httppost.setConfig(requestConfig);  //将Post请求赋值给requestConfig
            File outputfile = new File("image.jpg");    //定义一个图片接受
            ImageIO.write(bufferedImage, "jpg", outputfile);
            FileBody bin = new FileBody(outputfile);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("fileName", bin).build();
            httppost.setEntity(reqEntity);
            //System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httppost); //得到请求
                //System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String responseEntityStr = EntityUtils.toString(response.getEntity());
                    result= responseEntityStr;
                    if("null".equals(result)){
                        return null;
                    }
                    //System.out.println(responseEntityStr);
                    //System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                if(response!=null){
                    response.close();
                }
            }
        } catch (ClientProtocolException e) {
            System.out.println("null，空指针异常");
            //e.printStackTrace(); 
        } catch (IOException e) {
            System.out.println("null，空指针异常");
           // e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        return result;
    }

    //人脸识别，开始用户体验设备
    public static String openDevice(String url, BufferedImage bufferedImage, String category,String categoryName) {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);  //将url的值赋值给httppost
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
            httppost.setConfig(requestConfig);  //将Post请求赋值给requestConfig
            File outputfile = new File("image.jpg");    //定义一个图片接受
            ImageIO.write(bufferedImage, "jpg", outputfile);
            FileBody bin = new FileBody(outputfile);
            StringBody strCategory = new StringBody(category, Charset.forName("UTF-8"));
            StringBody strCategoryName = new StringBody(categoryName, Charset.forName("UTF-8"));
//            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("fileName", bin).build();

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//         multipartEntityBuilder.setCharset(Charset.forName("utf-8"));
            multipartEntityBuilder.setCharset(Charset.forName("utf-8"));
            multipartEntityBuilder.addPart("fileName", bin);
            multipartEntityBuilder.addPart("category",strCategory);
            multipartEntityBuilder.addPart("categoryName",strCategoryName);

            HttpEntity reqEntity = multipartEntityBuilder.build();

            httppost.setEntity(reqEntity);
//
            //System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httppost); //得到请求
                //System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String responseEntityStr = EntityUtils.toString(response.getEntity());
                    result = responseEntityStr;
                    if ("null".equals(result)) {
                        return null;
                    }
                    //System.out.println(responseEntityStr);
                    //System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (ClientProtocolException e) {
            System.out.println("null，空指针异常");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("null，空指针异常");
            // e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        return result;
    }

    //关闭用户体验设备
//    public static String closeDevice(String url, Long id, String name, String username, String facePhoto, Date startTime, String video, String category, String categoryName) {
    public static String closeDevice(String url, Integer id, String name, String video, String category, String categoryName){
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);  //将url的值赋值给httppost
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
            httppost.setConfig(requestConfig);  //将Post请求赋值给requestConfig
            File file = new File(video);
            FileInputStream input = new FileInputStream(file);
            FileBody bin = new FileBody(file);
            StringBody strId = new StringBody(String.valueOf(id), Charset.forName("UTF-8"));
//            StringBody strCategory = new StringBody(category, Charset.forName("UTF-8"));
//            StringBody strCategoryName = new StringBody(categoryName, Charset.forName("UTF-8"));
//            StringBody strName = new StringBody(name, Charset.forName("UTF-8"));
//            StringBody strVideo = new StringBody(video,Charset.forName("UTF-8"));
//            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("fileName", bin).build();

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setCharset(Charset.forName("utf-8"));
            multipartEntityBuilder.addPart("id",strId);
//            multipartEntityBuilder.addPart("category",strCategory);
//            multipartEntityBuilder.addPart("categoryName", strCategoryName);
//            multipartEntityBuilder.addPart("name",strName);
            multipartEntityBuilder.addPart("file",bin);

            HttpEntity reqEntity = multipartEntityBuilder.build();

            httppost.setEntity(reqEntity);
//
            //System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httppost); //得到请求
                //System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String responseEntityStr = EntityUtils.toString(response.getEntity());
                    result = responseEntityStr;
                    if ("null".equals(result)) {
                        return null;
                    }
                    //System.out.println(responseEntityStr);
                    //System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (ClientProtocolException e) {
            System.out.println("null，空指针异常");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("null，空指针异常");
            // e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        return result;
    }
}
