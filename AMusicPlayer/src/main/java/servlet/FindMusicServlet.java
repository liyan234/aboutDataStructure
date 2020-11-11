package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import model.Music;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FindMusicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        //获取一个session

        ObjectMapper mapper = new ObjectMapper();

        String musicName = req.getParameter("musicName");
        MusicDao musicDao = new MusicDao();
        List<Music> list = new ArrayList<>();


        if (musicName == null) {
            list = musicDao.selectAllMusic();
        } else {
            list = musicDao.selectMusicByKey(musicName);
        }
        mapper.writeValue(resp.getWriter(), list);
    }
}
