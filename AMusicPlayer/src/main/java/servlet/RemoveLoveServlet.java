package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RemoveLoveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        User user = (User) req.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        if (user == null) {
            map.put("msg", "未登录,请您登录");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        String strId = req.getParameter("id");
        int music_id = Integer.parseInt(strId);
        int user_id = user.getId();

        MusicDao musicDao = new MusicDao();
        if (musicDao.removeLoverMusic(user_id, music_id)  == 1) {
            map.put("msg", true);
        } else {
            map.put("msg", false);
        }
        mapper.writeValue(resp.getWriter(), map);
    }
}
