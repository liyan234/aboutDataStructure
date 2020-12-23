package dao;

import DBConnect.DBConnect;
import MyException.OrderSystemException;
import com.sun.org.apache.xpath.internal.operations.Or;
import model.Orders;
import model.Veg;
import sun.security.pkcs11.Secmod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//订单
public class OrdersDao {

    //新增订单
    public void add(Orders orders) throws OrderSystemException {
        //user和订单 关联的
        addOrders(orders);
        //订单和菜品表
        addOrders_veg(orders);
    }


    //这个是user和订单表相连
    private int addOrders(Orders orders) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int ret = 0;

        connection = DBConnect.getConnect();
        String sql = "insert into orders values(null, ?, now(), 0)";
        try {
            //获取自增主键的值
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setInt(1, orders.getUserId());

            ret = statement.executeUpdate();
            if (ret == 1) {
                //读取自增列
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    //把自增列放入orders，就是获取下标为第一个的
                    orders.setOrdersId(resultSet.getInt(1));
                }
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("新增订单失败");
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
    }

    //这个是订单表和菜品表相连
    private void addOrders_veg(Orders orders) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = DBConnect.getConnect();
        String sql = "insert into orders_veg values(?, ?);";
        try {
            connection.setAutoCommit(false);//改为手动 关闭自动
            statement = connection.prepareStatement(sql);
            List<Veg>  vegs = orders.getVegs();
            System.out.println(vegs);

            for (Veg v : vegs) {

                statement.setInt(1, orders.getOrdersId());
                statement.setInt(2, v.getVegId());
                statement.addBatch();//sql增加一个片段
            }
            statement.executeBatch();// 执行sql 假的执行 就是整合sql
            connection.commit();//把sql语句提交给服务器 是真的执行sql

        } catch (Exception e) {

            deleteOrdersUser(orders.getOrdersId());
            e.printStackTrace();
            //这个抛出异常  需要回滚第一次的插入操作，为了保证mysql的事务
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

    public void deleteOrdersUser(int ordersId) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;


        connection = DBConnect.getConnect();

        String sql = "delete from orders where ordersId=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ordersId);

            int ret = statement.executeUpdate();
            if (ret != 1) {
                throw new OrderSystemException("回滚失败");
            }

        } catch (SQLException e) {

            throw new OrderSystemException("回滚失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

    //查看自己的订单
    public List<Orders> selectOrdersByUserId(int userId) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Orders> list = new ArrayList<>();
        connection = DBConnect.getConnect();
        String sql = "select * from orders where userId=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setOrdersId(resultSet.getInt("ordersId"));
                orders.setUserId(resultSet.getInt("userId"));
                orders.setOrdertime(resultSet.getString("ordertime"));
                orders.setIsfinish(resultSet.getInt("isfinish"));
                list.add(orders);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("查看自己的订单失败");
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }

    }

    //查看所有订单
    public List<Orders> selectAllVeg() throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql = "select * from orders";
        connection = DBConnect.getConnect();
        List<Orders> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setOrdersId(resultSet.getInt("ordersId"));
                orders.setUserId(resultSet.getInt("userId"));
                orders.setOrdertime(resultSet.getString("ordertime"));
                orders.setIsfinish(resultSet.getInt("isfinish"));
                list.add(orders);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("查看订单失败");
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
    }

    //查看指定订单的信息
    public Orders selectOrdersByOrdersId(int ordersId) throws OrderSystemException {
        //A 根据ordersId得到一个Orders对象
        Orders orders = getOrders(ordersId);
        //B 根据orderId得到改orderId对应的菜品id列表
        List<Integer> vegIds = selectVegIds(ordersId);
        //C 根据菜品id列表 查询dishes表，获取到菜品详情
        orders = getVegDetail(orders, vegIds);
        return orders;
    }

    //  A
    private Orders getOrders(int ordersId) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        connection = DBConnect.getConnect();
        String sql = "select * from orders where ordersId = ?";
        Orders orders = new Orders();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ordersId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                orders.setOrdersId(resultSet.getInt("ordersId"));
                orders.setUserId(resultSet.getInt("userId"));
                orders.setOrdertime(resultSet.getString("ordertime"));
                orders.setIsfinish(resultSet.getInt("isfinish"));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("查找指定orders失败");
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
    }

    //  B
    private List<Integer> selectVegIds(int ordersId) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> list = new ArrayList<>();

        connection = DBConnect.getConnect();
        String sql = "select * from orders_veg where ordersId=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ordersId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer i = resultSet.getInt("vegId");
                list.add(i);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("查找vegIds失败");
        } finally {
            DBConnect.close(connection, statement, resultSet);
        }
    }

    // C
    private Orders getVegDetail(Orders orders, List<Integer> vegIds) throws OrderSystemException {

        List<Veg> vegs = new ArrayList<>();

        VegDao vegDao = new VegDao();
        for (Integer vegId: vegIds) {
            Veg veg = vegDao.selectVegById(vegId);
            vegs.add(veg);
        }
        orders.setVegs(vegs);
        return orders;
    }

    //修改订单的状态 将isfinish由0改为1
    public int changeOrdersState(int ordersId, int isfinish) throws OrderSystemException {
        Connection connection = null;
        PreparedStatement statement = null;
        int ret = 0;

        connection = DBConnect.getConnect();
        String sql = "update orders set isfinish=? where ordersId=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, isfinish);
            statement.setInt(2, ordersId);
            ret = statement.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderSystemException("修改订单状态失败");
        } finally {
            DBConnect.close(connection, statement, null);
        }
    }

}
