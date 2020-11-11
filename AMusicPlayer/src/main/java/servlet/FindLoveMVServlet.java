package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MVDao;
import model.MV;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindLoveMVServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        User user = (User) req.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        if (user == null) {
            map.put("msg", "未登录，请你登录");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        MVDao dao = new MVDao();
        List<MV> list = new ArrayList<>();
        String loveMVName = req.getParameter("loveMVName");

        int user_id = user.getId();

        if (loveMVName == null) {
            list = dao.selectLoveMVByUserId(user_id);
        } else {
            list = dao.selectLoveMVByKey(loveMVName, user_id);
        }
        mapper.writeValue(resp.getWriter(), list);
    }
}
