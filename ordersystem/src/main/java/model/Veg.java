package model;

public class Veg {
    private int vegId;
    private String name;
    private int price;

    public int getVegId() {
        return vegId;
    }

    public void setVegId(int vegId) {
        this.vegId = vegId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Veg{" +
                "vegId=" + vegId +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
