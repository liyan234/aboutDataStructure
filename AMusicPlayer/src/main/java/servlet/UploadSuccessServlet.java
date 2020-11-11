package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import model.Music;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadSuccessServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        //往数据库传文件
        String fileName = (String) req.getSession().getAttribute("fileName");
        String[] ss = fileName.split("\\.");

        String title = ss[0];

        String url = "music/" + title;

        String singer = req.getParameter("singer");

        //构建时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(new Date());

        User user = (User)req.getSession().getAttribute("user");
        int id = user.getId();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        Music music = new Music();
        music.setTitle(title);
        music.setSinger(singer);
        music.setTime(time);
        music.setUrl(url);
        music.setUserId(id);

        MusicDao musicDao = new MusicDao();

        int ret = musicDao.addMusic(music);
        System.out.println(ret);
        resp.sendRedirect("list.html");
    }
}
