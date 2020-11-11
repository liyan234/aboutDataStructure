package model;

public class MV {
    private int id;
    private String MVName;
    private String time;
    private String url;
    private int userId;

    @Override
    public String toString() {
        return "MV{" +
                "id=" + id +
                ", MVName='" + MVName + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                ", user_id=" + userId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMVName() {
        return MVName;
    }

    public void setMVName(String MVName) {
        this.MVName = MVName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
