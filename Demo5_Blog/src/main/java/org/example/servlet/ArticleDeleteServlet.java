package org.example.servlet;

import org.example.dao.ArticleDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleDelete")
public class ArticleDeleteServlet extends  AbstractBaseServlet{

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 未进行会话管理
        String strid = req.getParameter("ids");
        int num = ArticleDao.delete(strid.split(","));
        return num;
    }
}
