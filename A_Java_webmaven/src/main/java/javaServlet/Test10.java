package javaServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

public class Test10 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //收到图片，直接将图片保存到目录中

        //准备路径
        String basePath = "c:/Test1";
        Part image = req.getPart("yyy.jpg");

        String path = basePath + image.getSubmittedFileName();
        image.write(path);

        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("success");

    }
}
