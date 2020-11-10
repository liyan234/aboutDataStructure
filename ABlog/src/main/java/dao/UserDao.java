package dao;


import model.DBConnect;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//1. 新增（注册）用户
//2. 按照名字查找用户
//3. 根据id查找用户
public class UserDao {

    //新增用户
    public void addUser(User user) {
        //1.和数据库建立连接
        Connection connection = DBConnect.getConnection();
        //2.构建sql语句
        String sql = "insert into user values(null, ?, ?)";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,user.getName());
            statement.setString(2,user.getPassWord());

            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("插入失败");
                return;
            }
            System.out.println("插入成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.close(connection,statement,null);
        }
    }

    //按照名字查找用户
    public User selectUserByName(String name) {
        //获取连接
        Connection connection =  DBConnect.getConnection();
        //拼装sql语句
        String sql  = "select * from user where name = ?";
        //创建Pre
        PreparedStatement statement = null;
        //创建结果集对象
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            DBConnect.close(connection, statement, resultSet);
        }
        return null;
    }

    //根据userId查找User
    public User selectUserById (int id) {
        //获取连接
        Connection connection = null;
        connection = DBConnect.getConnection();
        //构建sql语句
        String sql = "select * from user where userId = ?";
        //构建Pre
        PreparedStatement statement = null;
        //构建结果集
        ResultSet resultSet = null;

        //拼装sql语句
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setUserId(resultSet.getInt("userId"));
                user.setPassWord(resultSet.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
        return null;
    }
}
