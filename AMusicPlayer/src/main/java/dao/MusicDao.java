package dao;


import DBConnect.DBConnect;
import model.LoverMusic;
import model.Music;
import sun.security.pkcs11.Secmod;

import javax.crypto.MacSpi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//对music数据库的操作
public class MusicDao {

    //1. 查询所有音乐
    public List<Music> selectAllMusic() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //构建一个链表
        List<Music> list = new ArrayList<>();
        //1.建立连接
        connection = DBConnect.getConnect();
        //2.构建sql语句
        String sql = "select * from music";
        try {
            statement = connection.prepareStatement(sql);
            //处理结果集
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getString("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userId"));
                list.add(music);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }

    // 根据id查询音乐
    public Music selectMusicById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBConnect.getConnect();
        String sql = "select * from music where id=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getString("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userId"));
                return music;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }
    //2. 根据关键字查询歌单
    public List<Music> selectMusicByKey(String key) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //构建一个链表
        List<Music> list = new ArrayList<>();
        //建立连接
        connection = DBConnect.getConnect();
        //构建sql语句
        String sql = "select * from music where title like '%" + key + "%'";

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getString("time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userId"));
                list.add(music);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }

    //3.上传音乐
    public int addMusic(Music music) {
        Connection connection = null;
        PreparedStatement  statement = null;

        connection = DBConnect.getConnect();
        String sql = "insert into music values(null, ?, ?, ?, ?,?)";
        int ret = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, music.getTitle());
            statement.setString(2, music.getSinger());
            statement.setString(3, music.getTime());
            statement.setString(4, music.getUrl());
            statement.setInt(5, music.getUserId());

            ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("插入失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            DBConnect.getClose(connection, statement, null);
        }
        return ret;
    }
    //函数重载
    public int addMusic(String title, String singer, String time,
                         String url, int userId) {
        Connection connection = null;
        PreparedStatement  statement = null;
        connection = DBConnect.getConnect();
        int ret = 0;
        String sql = "insert into music values(null, ?, ?, ?, ?,?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, singer);
            statement.setString(3, time);
            statement.setString(4, url);
            statement.setInt(5, userId);

            ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("插入失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            DBConnect.getClose(connection, statement, null);
        }
        return ret;
    }

    //4.删除歌曲 根据id来删除歌曲
    //并且要删除中间表 也就是 loveMusic中是否有这个歌 若是有 则要删除
    public int deleteMusicById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBConnect.getConnect();
        int ret = 0;
        //music中的id
        String sql = "delete from music where id=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ret = statement.executeUpdate();
            if (ret != 1) {
               // System.out.println("删除失败或者库里没有这首歌");
                return ret;
            }
            //删除成功 删除了总列表中的
            //然后我们查看我喜欢的列表中是否有这个音乐
            if (existLoveMusic(id)) {
                //存在于我喜欢的这个列表中
                //删除我喜欢的这个列表中的歌曲
                deleteLoverMusicById(id);
                return ret;
            }
            return ret;
            //不存在则已经删除成功了
           // System.out.println("删除成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, null);
        }
        return ret;
    }

    //删除我喜欢的列表中的那首歌曲
    private void deleteLoverMusicById(int music_id) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBConnect.getConnect();
        String sql = "delete from lovermusic where music_id=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, music_id);
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("删除失败");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, null);
        }
    }

    private boolean existLoveMusic(int music_id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBConnect.getConnect();
        String sql = "select * from lovermusic where music_id=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, music_id);
            resultSet = statement.executeQuery();
            //若是结果集 喜欢的列表中就存在这个歌曲
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return false;
    }

    //是否存在于lovermusic中
    private boolean existLoveMusic(int user_id, int music_id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = DBConnect.getConnect();
        String sql = "select * from lovermusic where music_id=? and user_id=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, music_id);
            statement.setInt(2, user_id);
            resultSet = statement.executeQuery();
            //若是结果集 喜欢的列表中就存在这个歌曲
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return false;
    }
    //5.添加音乐到喜欢的列表中
    public int addLoverMusic(int user_id, int music_id) {
        Connection connection = null;
        PreparedStatement statement = null;

        int ret = 0;
        if (existLoveMusic(user_id, music_id)) {
            return ret;
        }

        connection = DBConnect.getConnect();
        String sql = "insert into lovermusic values(null, ?, ?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, music_id);

            ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("添加失败");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, null);
        }
        return ret;
    }

    //6.移除当前用户所喜欢的音乐
    public int removeLoverMusic(int user_id, int music_id) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBConnect.getConnect();
        String sql = "delete from lovermusic where user_id=? and music_id=?";
        int ret = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, music_id);
            ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("删除失败");
                return ret;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, null);
        }
        return ret;
    }

    //7.查询当前用户所喜欢的所有音乐
    public List<Music> selectLoverMusicByUserId(int user_id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Music> list = new ArrayList<>();

        connection = DBConnect.getConnect();
        String sql = "select * from music where id in (select music_id from lovermusic where user_id=?)";
        try {

            statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                 Music music = new Music();
                 music.setId(resultSet.getInt("id"));
                 music.setTitle(resultSet.getString("title"));
                 music.setSinger(resultSet.getString("singer"));
                 music.setTime(resultSet.getString("Time"));
                 music.setUrl(resultSet.getString("url"));
                 music.setUserId(resultSet.getInt("userId"));
                 list.add(music);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, resultSet);
        }
        return null;
    }

    //8.关键字查询在这个用户的喜欢音乐的列表中
    public List<Music> selectLoverMusicByKey(String key, int user_id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Music> list = new ArrayList<>();

        connection = DBConnect.getConnect();
        String sql =
                "select * from music where title like'%" + key + "%' and id in " +
            "(select music_id from lovermusic where user_id=?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setTime(resultSet.getString("Time"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userId"));
                list.add(music);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getClose(connection, statement, null);
        }
        return null;
    }
}
