package servlet;

import dao.MVDao;
import model.MV;
import model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import view.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UploadMVServlet  extends HttpServlet {

    private final String PATH = "C:\\Users\\所谓爱隔山海\\Desktop\\java_lanuage\\AMusicPlayer\\src\\main\\webapp\\MV\\";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        HttpSession session = req.getSession(false);

        if (session == null) {
            String html = HtmlGenerator.getMessage("您未登录", "MV.html");
            resp.getWriter().write(html);
            return;
        }
        
        User user = (User) req.getSession().getAttribute("user");

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = null;

        //上传文件
        try {
            items = upload.parseRequest(req);

        } catch (FileUploadException e) {
            String html = HtmlGenerator.getMessage("传输发生未知错误", "MV.html");
            resp.getWriter().write(html);
            e.printStackTrace();
            return;
        }


        //获取第一个
        FileItem item = items.get(0);
        //获取文件名，便于插入数据库
        String fileName = item.getName();

        try {
            item.write(new File(PATH, fileName));
        } catch (Exception e) {
            String html = HtmlGenerator.getMessage("传输发生未知错误", "MV.html");
            resp.getWriter().write(html);
            e.printStackTrace();
            return;
        }

        //接下来就是上传数据库的数据
        //构建MVName
        String[] ss = fileName.split("\\.");
        String MVName = ss[0];
        //构建time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        //构建url
        String url = "MV/" + MVName;
        //获取userId
        int userId = user.getId();

        //构建MV对象
        MV mv = new MV();
        mv.setMVName(MVName);
        mv.setTime(time);
        mv.setUrl(url);
        mv.setUserId(userId);

        //获取MVDao对象
        MVDao dao = new MVDao();
        if (dao.uploadMV(mv) == 1) {
           resp.sendRedirect("MV.html");
        } else {
            resp.sendRedirect("uploadMV.html");
        }
    }
}
