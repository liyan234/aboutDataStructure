package servlet;


import MyException.OrderSystemException;
import com.google.gson.Gson;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//退出登录
@WebServlet("/logout")
public class logoutServlet extends HttpServlet {
    Gson gson = new Gson().newBuilder().create();

    static class Response{
        public int ok;
        public String reason;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Response response = new Response();
        try {
            HttpSession session = req.getSession();
            if (session == null) {
                throw new OrderSystemException("当前未登录");
            }
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new OrderSystemException("当前未登录");
            }
            //删除session
            session.removeAttribute("user");
            response.ok = 1;
            response.reason = "";
        } catch (OrderSystemException e) {
            response.ok = 0;
            response.reason = e.getMessage();
        } finally {
            resp.setContentType("application/json; charset=UTF-8");
            String gs = gson.toJson(response);
            resp.getWriter().write(gs);
        }
    }

}
