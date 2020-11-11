package model;

public class LoverMV {
    private int id;
    private int mv_id;
    private int user_id;

    @Override
    public String toString() {
        return "LoverMV{" +
                "id=" + id +
                ", mv_id=" + mv_id +
                ", user_id=" + user_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMv_id() {
        return mv_id;
    }

    public void setMv_id(int mv_id) {
        this.mv_id = mv_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
