package DBConnect;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//用于建立和mysql数据库的连接
public class DBConnect {

    private static volatile DataSource dataSource = null;

    private static String url = "jdbc:mysql://127.0.0.1:3306/musicserver?characterEncoding=utf-8&useSSL=true";
    private static String name = "root";
    private static String password = "liyan";

    //1.懒汉模式
    public static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (DBConnect.class) {
                if (dataSource == null) {
                    dataSource = new MysqlDataSource();
                    ((MysqlDataSource) dataSource).setURL(url);
                    ((MysqlDataSource) dataSource).setUser(name);
                    ((MysqlDataSource) dataSource).setPassword(password);
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnect() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getClose(Connection connection, PreparedStatement preparedStatement,
                             ResultSet resultSet) {
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
