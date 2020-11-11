package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MVDao;
import model.MV;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteMVServlet extends HttpServlet {


    private final String PATH = "C:\\Users\\所谓爱隔山海\\Desktop\\java_lanuage\\AMusicPlayer\\src\\main\\webapp\\";
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

        //获取mv的id
        String strId = req.getParameter("id");
        if (strId == null) {
            map.put("msg", false);
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        int id = Integer.parseInt(strId);
        int user_id = user.getId();

        MV mv = new MV();
        MVDao dao = new MVDao();
        mv = dao.selectMVById(id);
        int user_mv_id = mv.getUserId();
        if (user_mv_id != user_id) {
            map.put("msg", "你不是这个mv的上传人,无权删除");
            mapper.writeValue(resp.getWriter(), map);
            return;
        }

        //删除我喜欢的中的这个mv
        if (dao.existLoverMV(id, user_id)) {
            if(dao.removeLoverMV(id, user_id) ==1 ) {
                    if (dao.removeMV(id, user_id) == 1) {
                        //删除文件
                        File file = new File(PATH + mv.getUrl() + ".mp4");
                        if (file.delete()) {
                            //删除文件中的
                            map.put("msg", true);
                        } else {
                            map.put("msg", false);
                        }

                    } else {
                        map.put("msg", false);
                    }
            } else {
                map.put("msg", false);
            }
        } else {
            map.put("msg", false);
        }
        //然后继续删除
        mapper.writeValue(resp.getWriter(), map);
    }
}
