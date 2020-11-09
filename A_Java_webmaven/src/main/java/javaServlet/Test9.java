package javaServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

public class Test9 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.先获取Session 如果用户曾经没有访问过，此时创建一个session
        //若是用户访问过了，获取曾经的Session

        HttpSession httpSession = req.getSession(true);
        //判断是否为新用户
        Integer count = 1;
        if (httpSession.isNew()) {
            //新用户
            //把count值写入session中
            httpSession.setAttribute("count", count);
        } else {
            //老用户
            count = (Integer)httpSession.getAttribute("count");
            count = count + 1;
            httpSession.setAttribute("count", count);
        }

        //3.返回响应页面
        resp.setContentType("text/html; charset=utf-8");
        Writer writer = resp.getWriter();
        writer.write("<html>");
        writer.write("count: " + count);
        writer.write("<html>");


    }
}
