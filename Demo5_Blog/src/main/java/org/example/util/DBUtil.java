package org.example.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.MyException.AppException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "liyan";
    private static String URL = "jdbc:mysql://127.0.0.1:3306/servlet_blog?characterEncoding=utf-8&useSSL=true";

    private static final String URLttt = "jdbc:mysql://127.0.0.1:3306/servlet_blog?user=root&password=liyan&characterEncoding=utf-8&useSSL=true";

    // 使用final  引用不可改变

    private static  DataSource dataSource = null;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    // 上面dataSource 为null  必须new 一个 dataSource;
                    dataSource = new MysqlDataSource();
                    //((MysqlDataSource)dataSource).setURL(URLttt);

                    ((MysqlDataSource)dataSource).setURL(URL);
                    ((MysqlDataSource)dataSource).setUser(USERNAME);
                    ((MysqlDataSource)dataSource).setPassword(PASSWORD);
                }
            }
        }
        return dataSource;
    }
     public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            // 也可以抛自定义异常
            throw new AppException("DBCONNECT404", "获取数据库连接失败", e);
        }
    }

    /**
     * static 代码块出现错误， NoClassDefFoundError 表示可以找到，但是类加载失败无法运行
     *                      ClassNoyFoundException: 找不到类
     *
     *
     */
    /*static {
        datasource = new MysqlDatasource();
        ((MysqlDataSource)dataSource).setURL(URL);
        ((MysqlDataSource)dataSource).setUser(USERNAME);
        ((MysqlDataSource)dataSource).setPassword(PASSWORD);
    }


    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            // 也可以抛自定义异常
            throw new AppException("获取数据库连接失败", e);
        }
    }*/

    public static void Close(Connection connection , PreparedStatement statement,
                             ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            //
        } catch (SQLException e) {
            // 抛自定义框架
            throw new AppException("DBCLOSE404", "关闭数据库失败",  e);
        }
    }

}
