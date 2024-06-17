package cn.edu.bistu.majianglianliankan;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线节点内部类
 */
// 定义一个名为 LinkInfo 的类
public class LinkInfo {
    // 定义一个 List，用于存储连接的点
    private List<Point> points = new ArrayList<Point>();

    // 定义一个构造函数，接收两个 Point 对象作为参数
    public LinkInfo(Point p1, Point p2) {
        // 将这两个点添加到 points 列表中
        points.add(p1);
        points.add(p2);
    }

    // 定义一个构造函数，接收三个 Point 对象作为参数
    public LinkInfo(Point p1, Point p2, Point p3) {
        // 将这三个点添加到 points 列表中
        points.add(p1);
        points.add(p2);
        points.add(p3);
    }

    // 定义一个构造函数，接收四个 Point 对象作为参数
    public LinkInfo(Point p1, Point p2, Point p3, Point p4) {
        // 将这四个点添加到 points 列表中
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
    }

    // 定义一个名为 getLinkPoints 的方法，用于获取连接的点
    public List<Point> getLinkPoints() {
        // 返回 points 列表
        return points;
    }
}
