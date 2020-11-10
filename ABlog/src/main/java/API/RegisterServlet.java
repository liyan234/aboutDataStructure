package API;




//1.获取到前端提交到的数据（用户名，密码），校验是否合法
//2.拿着用户再数据库中查一下，看看当前用户是否已经存在，如果存在，认为注册失败
//3.拿着前端提交的数据，构造User对象并插入到数据库中
//4.返回一个结果页面，提示当前注册成功

import dao.UserDao;
import model.User;
import view.HtmlGenerator;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        //1.获取到前端提交到的数据
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        if (name == null || "".equals(name.trim())
        || password == null || "".equals(password.trim())) {
            String html = HtmlGenerator.getMessage("用户或者密码为空",
                    "register.html");
            resp.getWriter().write(html);
            return;
        }
        //2.获取数据库中的user对象看是否存在
        UserDao userDao = new UserDao();
        User existUser = userDao.selectUserByName(name);
        if (existUser != null) {
            String html = HtmlGenerator.getMessage("用户已经存在",
                    "register.html");
            resp.getWriter().write(html);
            return;
        }
        //3.从前端获取输入的 并注入到数据库中
        User user = new User();
        user.setName(name);
        user.setPassWord(password);
        userDao.addUser(user);
        //4.返回一个结果页面，提示注册成功
        String html = HtmlGenerator.getMessage("注册成功",
                "login.html");
        resp.getWriter().write(html);
    }
}
