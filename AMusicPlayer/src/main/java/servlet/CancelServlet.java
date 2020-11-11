package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CancelServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        UserDao userDao = new UserDao();

        if (userDao.deleteUser(username, password) == 1) {
            map.put("msg", true);
        } else {
            map.put("msg", false);
        }
        mapper.writeValue(resp.getWriter(), map);
    }
}
