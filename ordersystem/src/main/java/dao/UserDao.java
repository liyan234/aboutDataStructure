package dao;

import DBConnect.DBConnect;
import MyException.OrderSystemException;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    //注册
    public int addUser(User user) throws OrderSystemException {
        //建立连接
        Connection connection = null;
        PreparedStatement statement = null;
        int ret = 0;
        connection = DBConnect.getConnect();
        //sql 语句
        String sql = "insert into user values(null, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getIsAdmin());
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("新增user失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
        //返回值为1的时候就插入成功了
    }

    //登录
    public User login(String name, String password) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBConnect.getConnect();
        String sql = "select * from user where name=? and password=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setIsAdmin(resultSet.getInt("isAdmin"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("登录user失败");
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
        return null;
    }

    public User selectUserByName(String name) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBConnect.getConnect();
        String sql = "select * from user where name=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setIsAdmin(resultSet.getInt("isAdmin"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("登录user失败");
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
        return null;
    }

    //注销
    public int deleteUser(String name, String password) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        int ret = 0;

        connection = DBConnect.getConnect();
        String sql = "delete from user where name=? and password=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, password);
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("注销user失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }
}
