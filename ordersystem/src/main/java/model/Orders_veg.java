package model;

public class Orders_veg {
    private int ordersId;
    private int vegId;

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public int getVegId() {
        return vegId;
    }

    public void setVegId(int vegId) {
        this.vegId = vegId;
    }

    @Override
    public String toString() {
        return "orders_veg{" +
                "ordersId=" + ordersId +
                ", vegId=" + vegId +
                '}';
    }
}
