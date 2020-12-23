package dao;

import DBConnect.DBConnect;
import MyException.OrderSystemException;
import model.Veg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//菜品
public class VegDao {

    //新增菜品
    public int addVeg(Veg veg) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        int ret = 0;

        connection = DBConnect.getConnect();
        String sql = "insert into veg values(null, ?, ?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, veg.getName());
            statement.setInt(2, veg.getPrice());
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("新增菜品失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

    //删除菜品
    public int deleteVeg(String name) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        int ret = 0;

        connection = DBConnect.getConnect();
        String sql = "delete from veg where name=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("删除菜品失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

    //删除菜品
    public int deleteVeg(int vegId) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        int ret = 0;

        connection = DBConnect.getConnect();
        String sql = "delete from veg where vegId=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, vegId);
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("删除菜品失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

    //查找所有菜品
    public List<Veg> selectAllVeg() throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Veg> list = new ArrayList<>();

        connection = DBConnect.getConnect();
        String sql = "select * from veg";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Veg veg = new Veg();
                veg.setVegId(resultSet.getInt("vegId"));
                veg.setName(resultSet.getString("name"));
                veg.setPrice(resultSet.getInt("price"));
                list.add(veg);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("查找所有菜品失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

    public Veg selectVegById(int vegId) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Veg> list = new ArrayList<>();

        connection = DBConnect.getConnect();
        String sql = "select * from veg where vegId=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, vegId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Veg veg = new Veg();
                veg.setVegId(resultSet.getInt("vegId"));
                veg.setName(resultSet.getString("name"));
                veg.setPrice(resultSet.getInt("price"));
                return veg;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("查找菜品失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
        return null;
    }

}
