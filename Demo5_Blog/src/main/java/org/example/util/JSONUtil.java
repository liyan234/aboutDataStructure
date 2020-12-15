package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

// JSON 是前后端传输请求的数据格式
// 表单数据类型   x-www-form-urlencoded
public class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    // 静态方法
    /**
     * JSON序列化 ，将Java对象序列化成JSON字符串
     * @param o java对象
     * @return  json字符串
    * */
    public static String serialize (Object o) {
        try {
            // 将java对象序列化后写入
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("json序列化失败" + o);
        }
    }

    /**
     * 反序列化 将JSON字符串转化成JAVA对象
     * 使用泛型 来确定JAVA对象
     * @param is 输入流
     * @param tClass 确定要反序列化的类型
     * @return 反序列化的对象
     */
    public static <T> T deserialize (InputStream is, Class<T> tClass) {
        try {
            // 读取内容后将其反序列化成java对象
            return MAPPER.readValue(is, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("json反序列化失败" + tClass.getName());
        }
    }
    

}
