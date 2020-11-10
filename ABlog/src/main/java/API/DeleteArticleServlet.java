package API;

import dao.ArticleDao;
import model.Article;
import model.User;
import view.HtmlGenerator;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//1.验证用户的登陆状态
//2.读取请求内容，获取到要删除的文章Id
//3.根据文章的id查找到该文章的作者，若是作者 则执行删除操作，若不是 则不可以
//4.真正执行数据库的删除操作
//5.返回一个"删除成功"的操作

public class DeleteArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            String html = HtmlGenerator.getMessage("请先登录",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }
        User user = (User) httpSession.getAttribute("user");

        ArticleDao articleDao = new ArticleDao();
        String articleId = req.getParameter("articleId");
        if (articleId == null || "".equals(articleId.trim())) {
            String html = HtmlGenerator.getMessage("要删除的文章id有错误",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        Article article = articleDao.selectArticleByArticleId(Integer.parseInt(articleId));

        if (user.getUserId() != article.getUserId()) {
            String html = HtmlGenerator.getMessage("您无权删除这篇文章",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        //删除这篇文章
        articleDao.deleteArticleById(Integer.parseInt(articleId));
        String html = HtmlGenerator.getMessage("删除成功",
                "article");
        resp.getWriter().write(html);
    }
}
