package org.example.servlet;

import org.example.util.MyActionEnter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;

/**
 *  修改idea中 tomcat配置的应用上下文路径
 *  修改webapp/static/ueditor.config.js
 *  实现后端接口
 * */


@WebServlet("/ueditor")
public class UEditorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URL url = UEditorServlet.class.getClassLoader().
                getResource("config.json");
        // url 获取到的时编码后的字符串 需要先解码再使用
        // 根据的富文本编辑器提供上传功能
        String path = URLDecoder.decode(url.getPath(), "UTF-8");
        MyActionEnter enter = new MyActionEnter(req, path);
        String ex = enter.exec();// 执行
        PrintWriter pw = resp.getWriter();
        pw.println(ex);
        pw.flush();
        pw.close();
    }
}
