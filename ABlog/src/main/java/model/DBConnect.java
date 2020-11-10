package model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//管理数据库连接
//可以建立连接
//可以断开连接
//jdbc中使用 DataSource 来管理连接
//DataSource需要再稍微包装一层 单例模式
//单例模式管理唯一一个DataSource
//单例模式的实现 有两种风格 饿汉模式 和 懒汉模式
public class DBConnect {

    //3.volatile 内存可见性 保证每次获取都是在内存中获取值
    private static volatile DataSource dataSource = null;//静态成员
    private static final String url = "jdbc:mysql://127.0.0.1:3306/blogdemo?characterEncoding=utf-8&useSSL=true";
    private static final String name = "root";
    private static final String passWord = "liyan";

    //懒汉模式
    public static DataSource getDataSource() {
        //2.双重if
        if (dataSource == null) {
            //1.加锁
            synchronized (DBConnect.class) {
                if (dataSource == null) {
                    dataSource = new MysqlDataSource();
                    //给dataSource 设置属性
                    ((MysqlDataSource)dataSource).setURL(url);
                    ((MysqlDataSource)dataSource).setUser(name);
                    ((MysqlDataSource)dataSource).setPassword(passWord);
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() {
        //获取dataSource 后再建立连接
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection, PreparedStatement preparedStatement,
                             ResultSet resultSet) {
       //关闭的时候后创建的先关闭

        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
