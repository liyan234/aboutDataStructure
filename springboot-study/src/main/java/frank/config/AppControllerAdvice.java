package frank.config;


import frank.model.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//指定身份：拦截Controller/Web请求的类
@ControllerAdvice
public class AppControllerAdvice implements ResponseBodyAdvice<Object> {

    /*
    * 异常
    *  1 自定义异常 可以携带自定义的错误信息，可以给用户看
    *  2 jdk 和 框架，程序代码运行时抛出，携带的错误信息一般为英文，用户看不了
    * web项目中所起的作用
    *  */
    //处理请求方法中抛出的异常
    @ExceptionHandler(Exception.class)//指定捕获那种的异常
    @ResponseBody
    public Object handle(Exception e) {//传入的异常
        e.printStackTrace();
        return null;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;//执行Controller中的web请求方法的时候，返回数据到前端的时候，是否要重写响应体。
    }

    @Override
    public ResponseResult beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //如果存在返回字符串的情况，需要返回字符串类型，否则会报错
        //解决方案，判断body对象的类型，如果存在字符串，就返回ObjectMapper序列化的对象，否则返回一封装对象
        ResponseResult r = new ResponseResult();
        r.setSuccess(true);
        r.setDate(body);
        return r;
    }
}
