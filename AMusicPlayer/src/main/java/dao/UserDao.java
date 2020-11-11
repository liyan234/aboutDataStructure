package dao;

import DBConnect.DBConnect;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    //1.实现注册一个User
    public void addUser(User user) {
        //第一步 创建Pr
        Connection connection = null;
        PreparedStatement statement = null;
        //建立连接
        connection = DBConnect.getConnect();
        String sql = "insert into user values(null, ?, ?, ?, ?, ?) ";
        try {
            //拼装sql语句
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getGender());
            statement.setString(5, user.getEmail());

            //执行sql语句
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("插入失败");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            DBConnect.getClose(connection, statement, null);
        }
    }

    //2.登录获取User
    public User loginUser(String userName, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //获取连接
        connection = DBConnect.getConnect();
        // sql 语句
        String sql = "select * from user where username=? and password=?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAge(resultSet.getInt("age"));
                user.setGender(resultSet.getString("gender"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }

    public int deleteUser(String userName, String password) {
        //第一步 创建Pr
        Connection connection = null;
        PreparedStatement statement = null;
        //建立连接
        connection = DBConnect.getConnect();
        int ret = 0;
        String sql = "delete from user where username = ? and password = ?";
        try {
            //拼装sql语句
            statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);
            //执行sql语句
            ret = statement.executeUpdate();

            return ret;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            DBConnect.getClose(connection, statement, null);
        }
        return ret;
    }

}
