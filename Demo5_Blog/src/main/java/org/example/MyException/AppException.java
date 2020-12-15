package org.example.MyException;



/**
 * 自定义异常类： 业务代码抛自定义异常和其他异常
 *  自定义异常返回给的错误码，其他异常返回其他异常状态码
 * */
public class AppException extends RuntimeException {

    private String code;

    public AppException (String code,String message) {
      //  super(message);
      //  this.code = code;
        this(code, message, null);
    }

    public AppException (String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
