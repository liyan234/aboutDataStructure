package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import model.Music;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteServlet extends HttpServlet {

    //删除路径和上传的路径有点不同，
    private final String PATH= "C:\\Users\\所谓爱隔山海\\Desktop\\java_lanuage\\AMusicPlayer\\src\\main\\webapp\\";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        User user = (User)req.getSession().getAttribute("user");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        if (user == null) {
            map.put("msg", "未登录，请你登录");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        int user_id = user.getId();
        //获取music的id
        String strId = req.getParameter("id");
        if (strId == null) {
            map.put("msg", false);
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        int music_id = Integer.parseInt(strId);

        MusicDao musicDao = new MusicDao();
        Music music = new Music();
        music = musicDao.selectMusicById(music_id);
        int music_userId = music.getUserId();

        if (music_userId != user_id) {
            map.put("msg", "你不是这个音乐的上传人,无权删除");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        int ret = musicDao.deleteMusicById(music_id);

        if (ret == 1) {
            File file = new File(PATH + music.getUrl() + ".mp3");
            if (file.delete()) {
                //删除文件中的
                map.put("msg", true);
            } else {
                map.put("msg", false);
            }
        } else {
            map.put("msg", false);
        }

        mapper.writeValue(resp.getWriter(), map);
    }

}
