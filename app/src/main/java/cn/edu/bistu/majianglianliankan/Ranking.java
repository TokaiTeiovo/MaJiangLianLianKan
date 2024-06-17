package cn.edu.bistu.majianglianliankan;

/**
 * 玩家信息内部类
 */
public class Ranking {
    // 定义一个字符串，表示玩家的 ID
    private String id;
    // 定义一个字符串，表示玩家的名字
    private String name;
    // 定义一个字符串，表示玩家的时间
    private String time;
    // 定义一个字符串，表示日期
    private String date;
    public Ranking(String id, String name, String time, String date) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
