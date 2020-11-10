package dao;


import model.Article;
import model.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//1.新增博客
//2.删除博客
//3.查看博客列表
//4.查看博客内容详情

//JDBC 走
//  1，建立连接
//  2，构建sql语句
//  3，构建PrepareStatement中的statement 放入sql语句
//  4，执行sql语句 statement.executeUpdate
//  5, 关闭连接
// 若是有结果集 构建ResultSet 放入根据返回的结果集是链表还是一个对象 使用while和if循环
//  resultSet = statement.executeQuery();
public class ArticleDao {

    //1.新增博客
    public void addArticle(Article article) {

        //
        Connection connection = DBConnect.getConnection();

        String sql = "insert into article values(null, ?, ?, ?)";

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,article.getTitle());
            statement.setString(2,article.getContent());
            statement.setInt(3,article.getUserId());

            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("插入文章失败");
                return;
            }
            System.out.println("插入文章成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

    //删除博客
    //  1) 根据articleId删除
    public void deleteArticleById(int id) {

        Connection connection = DBConnect.getConnection();
        String sql = "delete from article where articleId = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("删除失败");
                return;
            }
            System.out.println("删除成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

    /*// 2) 根据文章名删除文章
    public void deleteArticleByTitle(String title) {
        Connection connection = DBConnect.getConnection();
        String sql = "delete from article where title = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,title);
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("删除失败");
                return;
            }
            System.out.println("删除成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }*/

    //3.查看博客列表 不用查看文章详情
    public List<Article> selectAll() {
        Connection connection = DBConnect.getConnection();
        String sql = "select articleId, title, userId from article";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Article article = new Article();
                article.setArticleId(resultSet.getInt("articleId"));
                article.setTitle(resultSet.getString("title"));
                article.setUserId(resultSet.getInt("userId"));
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
        return null;
    }

    //根据文章id查看文章详情
    public Article selectArticleByArticleId (int articleId) {
        Connection connection = DBConnect.getConnection();
        String sql = "select * from article where articleId = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Article article = new Article();
                article.setArticleId(resultSet.getInt("articleId"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setUserId(resultSet.getInt("userId"));
                return article;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
        return null;
    }
}
