import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class helloWorld extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        // 先写一个简单的 hello world
        // 请求无论是啥, 响应都是固定返回一个 hello world
        resp.getWriter().write("<h1>hello servlet</h1>");
    }
}
