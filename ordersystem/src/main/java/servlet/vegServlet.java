package servlet;


import MyException.OrderSystemException;
import MyReadBody.OrdersReaderBody;
import com.google.gson.Gson;
import dao.VegDao;
import model.User;
import model.Veg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dish")
public class vegServlet extends HttpServlet {
    Gson gson = new Gson().newBuilder().create();

    static class Request {
        public String name;
        public int price;
    }

    static class Response {
        public int ok;
        public String reason;
    }

    //新增菜品
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = new Response();
        req.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = req.getSession();
            if (session == null) {
                throw new OrderSystemException("未登录");
            }
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new OrderSystemException("未登录");
            }
            if (user.getIsAdmin() == 0) {
                throw new OrderSystemException("你不是管理员");
            }
            //读取req中的body
            String body = OrdersReaderBody.readBody(req);
            //转化成 gson 格式
            Request request = gson.fromJson(body, Request.class);
            String name = request.name;
            int price = request.price;
            Veg veg = new Veg();
            veg.setPrice(price);
            veg.setName(name);
            VegDao dao = new VegDao();
            int ret = dao.addVeg(veg);
            if (ret != 1) {
                throw new OrderSystemException("添加失败");
            }

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

    //删除菜品
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = new Response();
        req.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = req.getSession();
            if (session == null) {
                throw new OrderSystemException("未登录");
            }
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new OrderSystemException("未登录");
            }
            if (user.getIsAdmin() == 0) {
                throw new OrderSystemException("你不是管理员");
            }
            String strId = req.getParameter("dishId");
            if (strId == null) {
                throw new OrderSystemException("找不到菜品id");
            }

            int vegId = Integer.parseInt(strId);
            VegDao dao = new VegDao();
            int ret = dao.deleteVeg(vegId);
            System.out.println(ret);
            if (ret != 1) {
                throw new OrderSystemException("删除失败");
            }
            response.ok = 1;
            response.reason = "";
        } catch (OrderSystemException e) {
            System.out.println(123414);
            response.ok = 0;
            response.reason = e.getMessage();
        } finally {
            resp.setContentType("application/jason; charset=UTF-8");
            String gs = gson.toJson(response);
            resp.getWriter().write(gs);
        }
    }

    //查看所有菜品
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = new Response();
        req.setCharacterEncoding("UTF-8");
        List<Veg> list = new ArrayList<>();

        try {
            HttpSession session = req.getSession();
            if (session == null) {
                throw new OrderSystemException("未登录");
            }
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new OrderSystemException("未登录");
            }
            VegDao vegDao = new VegDao();
            list = vegDao.selectAllVeg();
        } catch (OrderSystemException e) {
            list = null;
        }
        String gs = gson.toJson(list);
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(gs);
    }
}
