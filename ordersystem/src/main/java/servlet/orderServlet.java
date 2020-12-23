package servlet;

import MyException.OrderSystemException;
import MyReadBody.OrdersReaderBody;
import com.google.gson.Gson;
import dao.OrdersDao;
import dao.VegDao;
import model.Orders;
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

@WebServlet("/order")
public class orderServlet extends HttpServlet {
    Gson gson = new Gson().newBuilder().create();

    static class Response{
        public int ok;
        public String reason;
    }

    //新增订单
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = new Response();
        req.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = req.getSession();
            if (session == null) {
                throw new OrderSystemException("当前未登录");
            }
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new OrderSystemException("当前未登录");
            }
            //从body中读取数据
            String body = OrdersReaderBody.readBody(req);
            Integer[] vegIds = gson.fromJson(body, Integer[].class);
            System.out.println(vegIds);

            //读取到了每一个菜的id
            Orders orders = new Orders();
            orders.setUserId(user.getUserId());
            List<Veg> list = new ArrayList<>();

            VegDao vegDao = new VegDao();
            for (Integer vegId : vegIds) {
                Veg veg = new Veg();
                veg = vegDao.selectVegById(vegId);
                list.add(veg);
            }
            orders.setVegs(list);

            OrdersDao ordersDao = new OrdersDao();
            ordersDao.add(orders);

            response.reason = "";
            response.ok = 1;

            System.out.println(111);
        } catch (OrderSystemException e) {
            response.ok = 0;
            response.reason = e.getMessage();
        } finally {
            String gs = gson.toJson(response);
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(gs);
        }
    }
    //查看订单

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<Orders> orders = new ArrayList<>();
        try {
            HttpSession session = req.getSession();
            if (session == null) {
                throw new OrderSystemException("当前未登录");
            }
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new OrderSystemException("当前未登录");
            }
            //普通用户获取到的订单信息

            OrdersDao dao = new OrdersDao();

            String strOrderId = req.getParameter("orderId");

            //为空查看所有
            if (strOrderId == null) {
                if (user.getIsAdmin() == 0) {
                    orders = dao.selectOrdersByUserId(user.getUserId());
                }

                if (user.getIsAdmin() == 1) {
                    orders = dao.selectAllVeg();
                }
                String gs = gson.toJson(orders);
                resp.setContentType("application/json; charset=UTF-8");
                resp.getWriter().write(gs);
            } else {
                //不为空就是查看指定订单信息
                int orderId = Integer.parseInt(strOrderId);
                Orders ord = dao.selectOrdersByOrdersId(orderId);
                if (user.getIsAdmin() == 0 && ord.getUserId() != user.getUserId()) {
                    throw new OrderSystemException("你不是管理员无权查看他人的订单");
                }

                String gs = gson.toJson(ord);
                System.out.println(ord);
                resp.setContentType("application/json; charset=UTF-8");
                resp.getWriter().write(gs);
            }

        } catch (OrderSystemException e) {
            String gs = gson.toJson(orders);
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(gs);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            if (user.getIsAdmin() == 0) {
                throw new OrderSystemException("你不是管理员");
            }

            String ordersId = req.getParameter("orderId");
            String isFinish = req.getParameter("isDone");

            if (ordersId == null || isFinish == null) {
                throw new OrderSystemException("参数有误");
            }

            int id = Integer.parseInt(ordersId);
            int is = Integer.parseInt(isFinish);

            OrdersDao dao = new OrdersDao();
            int ret = dao.changeOrdersState(id, is);
            if (ret != 1) {
                throw new OrderSystemException("修改状态失败");
            }
            response.ok = 1;
            response.reason = "";
        } catch (OrderSystemException e) {
            response.ok = 0;
            response.reason = e.getMessage();
        } finally {
            resp.setContentType("application/json; charset=UTF-8");
            String gs = gson.toJson(response);

        }
    }
}
