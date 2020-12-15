package org.example.model;


/**
 * http响应json数据， 前后端统一约定的json格式
 * 响应状态码都是200  进入ajax 的success来使用
 *  { success : true, data:xxx }
 *  { success : false, code : xxx}
 * */



// 使用注解的方式获取 get 和 set 方法
public class JSONResponse {
    // 业务操作是否成功
    private boolean success;
    // 业务操作的消息码  一般来说 出现错误时的错误码才有意义，用于定位问题
    private  String code;
    // 业务操作的错误消息 给用户看的信息
    private String message;
    // 业务数据， 业务操作成功时，给前端ajax success 方法使用，解析行用json数据，渲染网页
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
