package javaServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class Test1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Content-Type: 数据类型(text/html等)
        resp.setContentType("text/html; charset=utf-8");

        //返回请求主体中使用的字符编码的名称
        String encoding = req.getCharacterEncoding();
        // 返回请求主体的 MIME 类型，如果不知道类型则返回 null
        String contentType = req.getContentType();
        // 返回指示请求上下文的请求 URI 部分
        String contextPath = req.getContextPath();

        //User-Agent: 声明用户的操作系统和浏览器版本信息
        String ua = req.getHeader("User-Agent");
        //Host: 客户端告知服务器, 所请求的资源是在哪个主机的哪个端口上
        String host = req.getHeader("Host");
        // 返回请求的 HTTP 方法的名称，例如，GET、POST 或 PUT。
        String method = req.getMethod();
        //返回请求协议的名称和版本。
        String protocol = req.getProtocol();
        //返回包含在路径后的请求 URL 中的查询字符串。
        String queryString = req.getQueryString();
        //从协议名称直到 HTTP 请求的第一行的查询字符串中，返回该请求的 URL 的 一部分。
        String url = req.getRequestURI();
        //以字节为单位返回请求主体的长度，并提供输入流，或者如果长度未知则返 回 -1。
        int contentLength = req.getContentLength();

        Writer writer = resp.getWriter();
        writer.write("<html>");
        writer.write("encoding: " + encoding);
        writer.write("<br/>");
        writer.write("contentType: " + contentType);
        writer.write("<br/>");
        writer.write("contextPath" + contextPath);
        writer.write("<br/>");
        writer.write("ua: " + ua);
        writer.write("<br/>");
        writer.write("host: " + host);
        writer.write("<br/>");
        writer.write("method: "+ method);
        writer.write("<br/>");
        writer.write("protocol: " + protocol);
        writer.write("<br/>");
        writer.write("queryString: " + queryString);
        writer.write("<br/>");
        writer.write("url: " + url);
        writer.write("<br/>");
        writer.write("contentLength: " + contentLength);
        writer.write("</html>");
    }
}
