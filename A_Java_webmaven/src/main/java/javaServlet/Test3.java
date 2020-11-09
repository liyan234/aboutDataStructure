package javaServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

//遍历获取到header中的所有内容
public class Test3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");

        Writer writer = resp.getWriter();
        writer.write("<html>");
        Enumeration<String> headersName = req.getHeaderNames();
        while (headersName.hasMoreElements()) {
            //相当于next
            String header = headersName.nextElement();
            writer.write(header + ": " + req.getHeader(header));
            writer.write("<br/>");
        }

        writer.write("</html>");
    }
}
