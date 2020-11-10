package API;

import dao.UserDao;
import model.User;
import view.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//登录
//1.获取name，和 密码
//2.看数据库中是否有有这个名字
//3.登录成功后  并且要创建一个 session。
//          要得到这个用户的文章以及文章页面


public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        //判断是否为空
        if (name == null ||"".equals(name.trim())
        || password == null || "".equals(password.trim())) {
            String html = HtmlGenerator.getMessage("输入的账号或者密码为空",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }
        //看数据库中是否有这这个user
        UserDao userDao = new UserDao();
        User user = userDao.selectUserByName(name);
        if (user == null || !(user.getPassWord().trim()).equals(password.trim())) {
            String html = HtmlGenerator.getMessage("输入的账号或者密码错误",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }

        //获取session 若无session则创建一个session
        HttpSession httpSession = req.getSession(true);
        httpSession.setAttribute("user", user);
        //返回一个登录成功的状态
        String html = HtmlGenerator.getMessage("登录成功",
                "article");
        resp.getWriter().write(html);
    }
}
