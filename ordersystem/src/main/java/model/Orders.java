package model;

import java.util.List;

public class Orders {
    private int ordersId;
    private int userId;
    private String ordertime;
    private int isfinish;
    private List<Veg> vegs; //里面有多个菜品

    public List<Veg> getVegs() {
        return vegs;
    }

    public void setVegs(List<Veg> vegs) {
        this.vegs = vegs;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(int isfinish) {
        this.isfinish = isfinish;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "ordersId=" + ordersId +
                ", userId=" + userId +
                ", ordertime='" + ordertime + '\'' +
                ", isfinish=" + isfinish +
                ", vegs=" + vegs +
                '}';
    }
}
