package cn.edu.bistu.majianglianliankan;

import android.content.Context;

/**
 * 游戏信息内部类
 */
// 定义一个名为 GameConf 的类，用于存储游戏的配置信息
public class GameConf {
    // 定义静态变量，表示每个游戏块的宽度和高度
    public static int PIECE_WIDTH = 45;
    public static int PIECE_HEIGHT = 45;
    // 定义静态变量，表示默认的游戏时间
    public static int DEFAULT_TIME = 30;
    // 定义变量，表示游戏区域的宽度和高度
    private int xSize;
    private int ySize;
    // 定义变量，表示游戏区域的开始坐标
    private int beginImageX;
    private int beginImageY;
    // 定义变量，表示游戏的时间
    private long gameTime;
    // 定义变量，表示上下文对象
    private Context context;
    // 定义变量，表示屏幕的宽度和高度
    private int screenWidth;
    private int screenHeight;

    // 定义构造函数，接收游戏区域的宽度和高度，屏幕的宽度和高度，游戏时间和上下文对象作为参数
    public GameConf(int xSize, int ySize, int screenWidth, int screenHeight, long gameTime, Context context) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.gameTime = gameTime;
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // 获取屏幕的密度
        float scale = context.getResources().getDisplayMetrics().density;
        // 根据屏幕的密度调整游戏块的宽度和高度
        PIECE_WIDTH = (int) (PIECE_WIDTH * scale + 0.5f);
        PIECE_HEIGHT = (int) (PIECE_HEIGHT * scale + 0.5f);
        // 设置游戏区域的开始坐标
        setBeginImage();
    }

    // 定义方法，用于设置游戏区域的宽度
    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    // 定义方法，用于设置游戏区域的高度
    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    // 定义方法，用于设置游戏区域的开始坐标
    public void setBeginImage() {
        this.beginImageX = (screenWidth - PIECE_WIDTH * xSize) / 2;
        this.beginImageY = (screenHeight - 500 - PIECE_HEIGHT * ySize) / 2;
    }

    // 定义方法，用于获取游戏的时间
    public long getGameTime() {
        return gameTime;
    }

    // 定义方法，用于获取游戏区域的宽度
    public int getXSize() {
        return xSize;
    }

    // 定义方法，用于获取游戏区域的高度
    public int getYSize() {
        return ySize;
    }

    // 定义方法，用于获取游戏区域的开始 X 坐标
    public int getBeginImageX() {
        return beginImageX;
    }

    // 定义方法，用于获取游戏区域的开始 Y 坐标
    public int getBeginImageY() {
        return beginImageY;
    }

    // 定义方法，用于获取上下文对象
    public Context getContext() {
        return context;
    }
}