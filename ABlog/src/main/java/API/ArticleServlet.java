package API;

import dao.ArticleDao;
import dao.UserDao;
import model.Article;
import model.User;
import view.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


//获取article列表
//首先判断有没有session
//若是有session则用户已经登录，若是没有session, 则让用户登录
//判断请求中是否有articleId ,根据articleId去获取文章详情
//若没有articleId 则获取全部的article文章
public class ArticleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        HttpSession session = req.getSession(false);
        if (session == null) {
            String html = HtmlGenerator.getMessage("请先登录",
                    "login.html");
            return;
        }
        //获取user对象
        User user = (User) session.getAttribute("user");
        //判断是否有articleId对象
        String existArticleId = req.getParameter("articleId");
        if (existArticleId == null) {
            //获取文章列表
            getAllArticle(user, resp);
        } else {
            //获取这片文章的详情
            getOneArticle(Integer.parseInt(existArticleId), user, resp);
        }

    }

    private void getOneArticle(int articleId, User user, HttpServletResponse resp) throws IOException {
        //1.获取文章详情
        ArticleDao articleDao = new ArticleDao();
        Article article = articleDao.selectArticleByArticleId(articleId);
        if (article == null) {
            String html = HtmlGenerator.getMessage("文章不存在",
                    "article");
            resp.getWriter().write(html);
            return;

        }
        UserDao userDao = new UserDao();
        User author = userDao.selectUserById(article.getUserId());
        //2.构造页面
        String html = HtmlGenerator.getArticlePage(article, user, author);
        resp.getWriter().write(html);
    }

    private void getAllArticle(User user, HttpServletResponse resp) throws IOException {
        //1.获取文章列表
        ArticleDao articleDao = new ArticleDao();
        List<Article> articles = articleDao.selectAll();
        //2.构造页面
        String html = HtmlGenerator.getArticleListPage(articles, user);
        resp.getWriter().write(html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        //1. 获取session
        HttpSession session = req.getSession();
        //2.没有session 从新登录
        if (session == null) {
            String html = HtmlGenerator.getMessage("没有登录，请重新登录",
                    "login.html");
            resp.getWriter().write(html);
        }
        //3.有session 获取对象
        User user = (User) session.getAttribute("user");
        //4.从req中获取title 和 content
        int userId = user.getUserId();
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        //5.获取之后交互到数据库中
        if (title == null || "".equals(title.trim())
        || content == null || "".equals(content.trim())) {
            String html = HtmlGenerator.getMessage("请输入标题或者内容",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        //6.不为空的话就加入到数据库中
        ArticleDao articleDao = new ArticleDao();
        Article  article = new Article();
        article.setUserId(userId);
        article.setTitle(title);
        article.setContent(content);
        articleDao.addArticle(article);
        String html = HtmlGenerator.getMessage("发布成功", "article");
        resp.getWriter().write(html);
        return;
    }
}
