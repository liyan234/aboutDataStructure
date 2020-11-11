package servlet;

import model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import view.HtmlGenerator;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UploadServlet extends HttpServlet {

    private final String PATH = "C:\\Users\\所谓爱隔山海\\Desktop\\java_lanuage\\AMusicPlayer\\src\\main\\webapp\\music\\";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        HttpSession session = req.getSession(false);
        if (session == null) {
            String html = HtmlGenerator.getMessage("您未登录", "list.html");
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
            e.printStackTrace();
            return;
        }

        //获取第一个
        FileItem item = items.get(0);

        String fileName = item.getName();
        //创建一个fileName的session
        req.getSession().setAttribute("fileName",fileName);

        try {
            item.write(new File(PATH, fileName));
        } catch (Exception e) {
            String html = HtmlGenerator.getMessage("传输发生未知错误", "list.html");
            resp.getWriter().write(html);
            e.printStackTrace();
            return;
        }

        //跳转到下一个页面
        resp.sendRedirect("uploadsuccess.html");
    }
}
