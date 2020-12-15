package org.example.servlet;

import org.example.MyException.AppException;
import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public abstract class AbstractBaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        // 设置响应体的编码格式
        resp.setCharacterEncoding("UTF-8");
        // 设置响应体的数据类型  浏览器采取那种方式去执行
        resp.setContentType("application/json");

        //Session会话管理：除登录和注册接口，其他都需要登录后访问
        //req.getServletPath(); 获取请求的服务路径

        JSONResponse JSresp = new JSONResponse();

        try {

            //调用子类重写的方法
            Object data = process(req, resp);

            // 子类的process 方法操作成功，没有抛异常，表示业务执行成功
            JSresp.setSuccess(true);
            JSresp.setData(data);

        } catch (Exception e){
            //异常如何处理 JDBC的异常SQLException JSON处理的异常
            //自定义异常来返回异常处理的方式
            // 传入前端的异常信息

            e.printStackTrace();
            String code = "UNKNOWN";
            String s = "服务器未知错误";
            if (e instanceof AppException) {
                code = ((AppException) e).getCode();
                s = e.getMessage();
            }

            // 可以不用设置  boolean的基本数据类型false
            JSresp.setSuccess(false);
            JSresp.setCode(code);
            JSresp.setMessage(s);
        }
        PrintWriter p = resp.getWriter();
        // 调用自己写的序列化方法  序列化JSresp对象
        p.println(JSONUtil.serialize(JSresp));
        p.flush();
        p.close();
    }

    // protected : 子类可以用
    protected abstract Object process(HttpServletRequest req,
                                    HttpServletResponse resp) throws Exception;

}
