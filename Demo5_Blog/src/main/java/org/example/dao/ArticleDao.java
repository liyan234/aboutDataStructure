package org.example.dao;

import org.example.MyException.AppException;
import org.example.model.Article;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDao {

    public static int insert(Article article) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBUtil.getConnection();
        String  sql = "insert into article (title, content, user_id)" +
                "values (?, ?, ?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getContent());
            statement.setInt(3, article.getUser_id());
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new  AppException("ARTINSERT005", "新增出错", throwables);
        } finally {
            DBUtil.Close(connection, statement, null);
        }
    }

    public static Article query(int parseInt) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBUtil.getConnection();
        String sql = "select id,title,content from article where id = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, parseInt);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
              Article article = new Article();
              article.setId(parseInt);
              article.setTitle(resultSet.getString("title"));
              article.setContent(resultSet.getString("content"));
              return article;
            }
            return null;
        } catch (SQLException throwables) {
            throw new AppException("ARTDETAIL", "查询文章详情失败", throwables);
        } finally {
            DBUtil.Close(connection, statement, resultSet);
        }

    }

    public static int updateArticle(Article article) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBUtil.getConnection();
        String sql = "update article set content = ? ,title = ?  where id = ? ;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, article.getContent());
            statement.setString(2, article.getTitle());
            statement.setInt(3, article.getId());
            
            int num = statement.executeUpdate();
            return num;
        } catch (SQLException throwables) {
            throw new AppException("UPDATEART007", "修改失败", throwables);
        } finally {
            DBUtil.Close(connection, statement, null);
        }

    }

    public List<Article> queryByuUser_id(Integer user_id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> list = new ArrayList<>();
        connection = DBUtil.getConnection();
        String sql = "select * from article where user_id = ?";
        //String sql  = "select id, title from article where user_id=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setContent(resultSet.getString("content"));

                article.setTitle(resultSet.getString("title"));
                article.setUser_id(resultSet.getInt("user_id"));
                article.setView_count(resultSet.getInt("view_count"));
                java.sql.Date date = resultSet.getDate("create_time");
                if (date != null) {
                    article.setCreate_time(new Date(date.getTime()));
                }
                list.add(article);
            }
            return list;
        } catch (SQLException e) {
            throw new AppException("ART001", "获取文章列表失败");
        } finally {
            DBUtil.Close(connection, statement, resultSet);
        }
    }

    public static int delete(String[] split) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBUtil.getConnection();
        StringBuilder sql = new StringBuilder("delete from article where id in (");
        sql.append("?");
        for (int i = 1; i < split.length; i++) {
            sql.append(",");
            sql.append("?");
        }
        sql.append(")");
        try {
            statement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < split.length; i++) {
                statement.setInt(i+1, Integer.parseInt(split[i]));
            }
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new AppException("ARTDELE001", "文章删除出错", throwables);
        } finally {
            DBUtil.Close(connection, statement, null);
        }
    }
}
