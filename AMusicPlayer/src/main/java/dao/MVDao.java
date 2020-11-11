package dao;

import DBConnect.DBConnect;
import model.MV;
import model.Music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MVDao {

    //上传MV
    public int uploadMV(MV mv) {
        Connection connection = null;
        PreparedStatement statement = null;

        //获取连接
        connection = DBConnect.getConnect();
        String sql = "insert into mv values(null, ?, ?, ?, ?)";
        int ret = 0;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, mv.getMVName());
            statement.setString(2, mv.getTime());
            statement.setString(3, mv.getUrl());
            statement.setInt(4, mv.getUserId());

            ret = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, null);
        }

        return ret;
    }


    public MV selectMVById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBConnect.getConnect();
        String sql = "select * from mv where id = ?";
        MV mv = new MV();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                mv.setId(resultSet.getInt("id"));
                mv.setMVName(resultSet.getString("mvname"));
                mv.setTime(resultSet.getString("time"));
                mv.setUrl(resultSet.getString("url"));
                mv.setUserId(resultSet.getInt("userId"));
            }
            return mv;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }

    //查找MV
    public List<MV> selectAllMV() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBConnect.getConnect();
        List<MV> mvs = new ArrayList<>();
        String sql = "select * from mv";

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                MV mv = new MV();
                mv.setId(resultSet.getInt("id"));
                mv.setMVName(resultSet.getString("mvname"));
                mv.setTime(resultSet.getString("time"));
                mv.setUrl(resultSet.getString("url"));
                mv.setUserId(resultSet.getInt("userId"));
                mvs.add(mv);
            }
            return mvs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }

    //根据关键字查询MV
    public List<MV> selectMVByKey(String key) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //构建一个链表
        List<MV> list = new ArrayList<>();
        //建立连接
        connection = DBConnect.getConnect();
        //构建sql语句
        String sql = "select * from mv where mvname like '%" + key + "%'";

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MV mv = new MV();
                mv.setId(resultSet.getInt("id"));
                mv.setMVName(resultSet.getString("mvname"));
                mv.setTime(resultSet.getString("time"));
                mv.setUrl(resultSet.getString("url"));
                mv.setUserId(resultSet.getInt("userId"));
                list.add(mv);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }

    //移除MV
    public int removeMV(int mv_id, int userId){
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBConnect.getConnect();
        int ret = 0;
        if (existLoverMV(mv_id, userId)) {
            removeLoverMV(mv_id, userId);
        }
        String sql = "delete from mv where id=? and userId =?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, mv_id);
            statement.setInt(2, userId);
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement ,null);
        }
        return ret;
    }

    public boolean existLoverMV(int mv_id, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        connection = DBConnect.getConnect();

        boolean bool = false;

        String sql = "select * from lovermv where mv_id=? and user_id=?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, mv_id);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bool = true;
            }
            return bool;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement ,null);
        }
        return bool;
    }


    //移除我喜欢的mv
    public int removeLoverMV(int mv_id, int user_id) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBConnect.getConnect();
        int ret = 0;
        String sql = "delete from lovermv where mv_id=? and user_id =?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, mv_id);
            statement.setInt(2, user_id);
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement ,null);
        }
        return ret;
    }

    //
    public int removeLoverMV(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBConnect.getConnect();
        int ret = 0;
        String sql = "delete from lovermv where id=?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement ,null);
        }
        return ret;
    }


    //添加我喜欢的mv
    public int addLoverMV(int mv_id, int user_id) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBConnect.getConnect();
        int ret = 0;
        String sql = "insert into lovermv values(null, ?, ?)";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, mv_id);
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement ,null);
        }
        return ret;
    }


    public List<MV> selectLoveMVByKey(String key, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //构建一个链表
        List<MV> list = new ArrayList<>();
        //建立连接
        connection = DBConnect.getConnect();
        //构建sql语句
        String sql = "select * from mv where id in (select mv_id from lovermv where mvname like '%" + key + "%' and user_id = ?)" ;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MV mv = new MV();
                mv.setId(resultSet.getInt("id"));
                mv.setMVName(resultSet.getString("mvname"));
                mv.setTime(resultSet.getString("time"));
                mv.setUrl(resultSet.getString("url"));
                mv.setUserId(resultSet.getInt("userId"));
                list.add(mv);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }

    public List<MV> selectLoveMVByUserId(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //构建一个链表
        List<MV> list = new ArrayList<>();
        //建立连接
        connection = DBConnect.getConnect();
        //构建sql语句
        String sql = "select * from mv where id in (select mv_id from lovermv where user_id = ?)" ;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MV mv = new MV();
                mv.setId(resultSet.getInt("id"));
                mv.setMVName(resultSet.getString("mvname"));
                mv.setTime(resultSet.getString("time"));
                mv.setUrl(resultSet.getString("url"));
                mv.setUserId(resultSet.getInt("userId"));
                list.add(mv);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }




}
