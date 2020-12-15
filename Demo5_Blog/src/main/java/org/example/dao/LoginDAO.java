package org.example.dao;

import org.example.MyException.AppException;
import org.example.model.User;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.Date;

public class LoginDAO {
    public User query (String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBUtil.getConnection();
        String sql = "select * from user where username=?";
        try {

            statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setNickname(resultSet.getString("nickname"));


                //返回值是 时分秒 java.sql.Date
                //getTimestamp java.sql.Timestamp 年月日时分秒
                //获取的时数据库中时间类型 sql.Date
                java.sql.Date birthday = resultSet.getDate("birthday");

                if (birthday != null) {
                    // 需要转化类型 将sql.Date 装换成 util.Date
                    user.setBirthday(new Date(birthday.getTime()));
                }

                user.setSex(resultSet.getBoolean("sex"));
                user.setHead(resultSet.getString("head"));
                return user;
            }
        } catch (SQLException e) {
            throw new AppException("DAOLOG001", "查询用户错误", e);
        } finally {
            DBUtil.Close(connection, statement, resultSet);
        }
        return null;
    }

}
