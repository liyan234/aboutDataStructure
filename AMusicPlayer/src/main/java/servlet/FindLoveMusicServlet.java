package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import model.Music;
import model.User;
import sun.font.CreatedFontTracker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindLoveMusicServlet extends HttpServlet {

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

        String musicName = req.getParameter("loveMusicName");
        List<Music> musics = new ArrayList<>();
        MusicDao musicDao = new MusicDao();
        int use_id = user.getId();

        if (musicName == null){
            musics = musicDao.selectLoverMusicByUserId(use_id);
        } else {
            musics = musicDao.selectLoverMusicByKey(musicName, use_id);
        }
        mapper.writeValue(resp.getWriter(), musics);

    }

}
