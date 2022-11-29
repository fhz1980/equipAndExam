package com.sznikola.equipAndExam.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-11-09  16:26
 */
public class UploadUtil {

   private UploadUtil(){}

   public static UploadUtil build(){
      return new UploadUtil();
   }

   private  ObjectMapper om = new ObjectMapper();
   private  RestTemplate restTemplate;

   {

      RestTemplate restTemplate1 = SpringContextUtil.getBean("restTemplate", RestTemplate.class);

      if (Objects.isNull(restTemplate1)){
         restTemplate1 = new RestTemplate();
      }

      restTemplate = restTemplate1;
   }

   /**
    * @description:
    * @author: YOUNG
    * @data 2022/11/9 16:29
    * @param: url 文件上传路径
    *    <p>http://192.168.1.254:8888/upload/{path}</p>
    * @param file 本地文件路径
    * @return: boolean
    **/
   public boolean uploadFile(String path, File file){

      //掉来安全帽的文件名，然后这里就把路径全部输出
      String mainService = ParameterOperate.extract("mainService");

      // 接口路径都是 / 的，那个是文件路径按平台自动适应
      if(!mainService.endsWith("/")){
         mainService += "/";
      }

      String url = MessageFormat.format("{0}{1}{2}", mainService, "upload/", path);

      // 1、封装请求头
      HttpHeaders headers = new HttpHeaders();
      MediaType type = MediaType.parseMediaType("multipart/form-data");
      headers.setContentType(type);
      headers.setContentLength(file.length());

      headers.setContentDispositionFormData("file", file.getName());

      // 2、封装请求体
      MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
      FileSystemResource resource = new FileSystemResource(file);
      param.add("file", resource);

      // 3、封装整个请求报文
      HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(param, headers);

      // 4、发送请求
      ResponseEntity<String> data = restTemplate.postForEntity(url, formEntity, String.class);

      Map map = null;
      try {
         map = om.readValue(data.getBody(), Map.class);
      } catch (JsonProcessingException e) {
         throw new RuntimeException(e);
      }
      if (Objects.isNull(map)){
         return false;
      }

      Integer code = (Integer)map.get("code");

      if(Objects.nonNull(code) && code == 2000){
           return true;
      }
      return false;
   }

   public boolean sendMsg(Map<String, Object> param){

      //安全帽的文件名，然后这里就把路径全部输出
      String mainService = ParameterOperate.extract("mainService");

      // 接口路径都是 / 的，那个是文件路径按平台自动适应
      if(!mainService.endsWith("/")){
         mainService += "/";
      }

      String url = MessageFormat.format("{0}{1}{2}", mainService, "msg/");

      Map map = restTemplate.postForObject(url, param, Map.class);

      if (Objects.isNull(map)){
         return false;
      }

      Integer code = (Integer)map.get("code");

      if(Objects.nonNull(code) && code == 2000){
         return true;
      }
      return false;
   }
}
