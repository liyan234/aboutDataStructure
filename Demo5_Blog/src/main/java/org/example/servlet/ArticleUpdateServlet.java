package org.example.servlet;

import org.example.dao.ArticleDao;
import org.example.model.Article;
import org.example.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;


@WebServlet("/articleUpdate")
public class ArticleUpdateServlet extends AbstractBaseServlet {

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 获取请求中的输入流
        InputStream inputStream = req.getInputStream();
        // 反序列化
        Article article = JSONUtil.deserialize(inputStream, Article.class);
        int num = ArticleDao.updateArticle(article);
        return num;
    }
}
