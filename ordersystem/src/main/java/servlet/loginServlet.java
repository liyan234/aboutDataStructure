package servlet;


import MyException.OrderSystemException;
import MyReadBody.OrdersReaderBody;
import com.google.gson.Gson;
import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/login")
public class loginServlet extends HttpServlet {

    Gson gson = new Gson().newBuilder().create();

    static class Request {
        public String name;
        public String password;
    }

    static class Response {
        public int ok;
        public String reason;// 失败的时候表示失败原因
        public String name;
        public int isAdmin;
    }


    //这个post请求是登录请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Response response = new Response();
        //1. 读取body中数据
        //2. 把body解析成Gson格式
        //3. 查询数据库中中是否有这个User，有则登陆成功，并且设置session
        //4. 反应数据
        try {
            String body = OrdersReaderBody.readBody(req);
            Request request = gson.fromJson(body, Request.class);
            UserDao dao = new UserDao();
            User user = dao.login(request.name, request.password);
            if (user == null) {
                throw new OrderSystemException("名字或者密码输入错误，请重新登录");
            }
            //不为空的就是登录成功了 然后设置session
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            //设置session成功之后就可以反应数据了
            response.ok = 1;
            response.reason = "";
            response.name = user.getName();
            response.isAdmin = user.getIsAdmin();
        } catch (OrderSystemException e) {
            response.ok = 0;
            response.reason = e.getMessage();
        } finally {
            String jsString = gson.toJson(response);
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(jsString);
        }
    }

    //这个get请求是查询是否为登录状态
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Response response = new Response();
        //1。获取session
        try {
            //检查session
            HttpSession session = req.getSession(false);
            if (session == null) {
                throw new OrderSystemException("未登录状态");
            }
            //检查user
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new OrderSystemException("未登录状态");
            }
            response.ok = 1;
            response.reason = "";
            response.name = user.getName();
            response.isAdmin = user.getIsAdmin();
        } catch (OrderSystemException e) {
            response.ok = 0;
            response.reason = e.getMessage();
        } finally {
            String gsString = gson.toJson(response);
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(gsString);
        }
    }
}
