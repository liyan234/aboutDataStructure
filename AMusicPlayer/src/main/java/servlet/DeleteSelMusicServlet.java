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

public class DeleteSelMusicServlet extends HttpServlet {

    private final String PATH= "C:\\Users\\所谓爱隔山海\\Desktop\\java_lanuage\\AMusicPlayer\\src\\main\\webapp\\";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        User user = (User)req.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map= new HashMap<>();

        if (user == null) {
            map.put("msg","未登录,请您登录");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        String[] values = req.getParameterValues("id[]");
        if (values == null || values.equals("")) {
            map.put("msg","未选中任何");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }
        int length = values.length;

        int[] music_ids = new int[length];

        for (int i = 0; i < length; i++) {
            music_ids[i] = Integer.parseInt(values[i]);
        }

        MusicDao musicDao = new MusicDao();
        int user_id = user.getId();

        Music[] musics = new Music[length];
        for (int i = 0; i < length; i++) {
            Music music = new Music();
            music = musicDao.selectMusicById(music_ids[i]);
            int music_user_id = music.getUserId();

            if (music_user_id != user_id) {
                map.put("msg", "您删除的一部分音乐不是自己上传的");
                mapper.writeValue(resp.getWriter(), map);
                return;
            }
            musics[i] = music;
        }
        for (int i = 0; i < length; i++) {
            musicDao.deleteMusicById(music_ids[i]);
        }
        //删除文件
        int sum = 0;
        for (int i = 0; i < length; i++) {
            File file = new File(PATH + musics[i].getUrl() + ".mp3");
            if (file.delete()) {
                sum ++;
            }
        }
        if (sum == length) {
            map.put("msg",true);
        } else {
            map.put("msg",false);
        }
        mapper.writeValue(resp.getWriter(), map);
    }
}
