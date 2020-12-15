package org.example.servlet;

import org.example.MyException.AppException;
import org.example.dao.LoginDAO;
import org.example.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginServlet extends AbstractBaseServlet{

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        LoginDAO dao = new LoginDAO();
        User user = dao.query(username);

        if (user == null) {
            throw new AppException("LOG002", "用户不存在");
        }

        if (!user.getPassword().equals(password)) {
            throw new AppException("LOG002", "用户名或者密码错误");

        }
        /*if ("abc".equals(username)) {
            // 模拟登录成功
            return null;
            //resp.sendRedirect("jsp/articleList.jsp");
        } else {
            throw new AppException("LOGIN001", "用户名或密码错误");
        }*/

        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return null;
    }
}
