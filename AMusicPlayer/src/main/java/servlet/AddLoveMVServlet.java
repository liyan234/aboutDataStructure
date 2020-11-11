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
import java.util.HashMap;
import java.util.Map;

public class AddLoveMVServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        //添加我喜欢的mv
        User user = (User) req.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        if (user == null) {
            map.put("msg", "未登录，请你登录");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        int user_id = user.getId();

        MVDao dao = new MVDao();
        //我喜欢的mv中已经加入了这个音乐
        if (dao.existLoverMV(id, user_id)) {
            map.put("msg", false);
            mapper.writeValue(resp.getWriter(), map);
            return;
        }
        //没有添加过这个mv才可以添加
        if (dao.addLoverMV(id, user_id) == 1) {
            map.put("msg", true);
        } else {
            map.put("msg", false);
        }
        mapper.writeValue(resp.getWriter(), map);
    }
}
