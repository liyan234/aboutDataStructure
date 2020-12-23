package servlet;

import MyException.OrderSystemException;
import MyReadBody.OrdersReaderBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.UserDao;
import model.User;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//登录的API
@WebServlet("/register")
public class registerServlet extends HttpServlet {

    //先去创建一个Gson ,为下来的代码做准备
    private Gson gson= new GsonBuilder().create();

    static class Request {
        public String name;
        public String password;
    }

    static class Response{
        public int ok;
        public String reason;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //1.读取body中数据
        //2.把body解析成 Request对象 GSON格式
        //3.插寻数据库 ，看数据库中是否存在当前的用户名
        //4.把提交的数据构建成User ，插入数据库
        //5.构造响应数据
        Response response = new Response();
        try {
            //1。读取body中数据
            String body = OrdersReaderBody.readBody(req);
            //2.把body解析成 Request对象 GSON格式
            Request request = gson.fromJson(body, Request.class);
            //3插寻数据库 ，看数据库中是否存在当前的用户名
            UserDao dao = new UserDao();
            User existUser = dao.selectUserByName(request.name);
            if (existUser != null) {
                //抛出异常的话直接别catch处理 然后的话
                throw new OrderSystemException("这个name已经存在");
            }
            //4.把提交的数据构建成User ，插入数据库
            User user = new User();
            user.setName(request.name);
            user.setPassword(request.password);
            user.setIsAdmin(0);
            //注册
            if (dao.addUser(user) != 1) {
                throw new OrderSystemException("注册失败");
            }
            response.ok = 1;
            response.reason = "";

        } catch (OrderSystemException e) {
            e.printStackTrace();
            response.ok = 0;
            response.reason = e.getMessage();
        } finally {
            //5.构造响应数据
            resp.setContentType("application/json; charset=UTF-8");
            String jsString = gson.toJson(response);
            resp.getWriter().write(jsString);
        }

        }
    }

