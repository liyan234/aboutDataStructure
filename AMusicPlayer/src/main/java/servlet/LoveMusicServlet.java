package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoveMusicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            map.put("msg","未登录，请你登录");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        String strMusicId = req.getParameter("id");
        int music_id = Integer.parseInt(strMusicId);
        int user_id = user.getId();
        MusicDao musicDao = new MusicDao();
        int ret = musicDao.addLoverMusic(user_id, music_id);

        if (ret != 1) {
            map.put("msg", false);
        } else {
            map.put("msg", true);
        }
        mapper.writeValue(resp.getWriter(), map);
    }
}
