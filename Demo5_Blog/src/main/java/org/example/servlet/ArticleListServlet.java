package org.example.servlet;

import org.example.MyException.AppException;
import org.example.dao.ArticleDao;
import org.example.model.Article;
import org.example.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/articleList")
public class ArticleListServlet extends AbstractBaseServlet{

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 获取session
        HttpSession  session = req.getSession(false);
        // 如果没有 返回null

        if (session == null) {
            throw new AppException("ART002", "session为空, 用户未登录");
        }
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new AppException("ART002", "session不为空, 但没有user");
        }
        Integer user_id = user.getId();
        ArticleDao dao = new ArticleDao();
        List<Article> articles = dao.queryByuUser_id(user_id);

        return articles;
    }
}
