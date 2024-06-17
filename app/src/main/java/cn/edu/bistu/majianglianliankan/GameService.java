package cn.edu.bistu.majianglianliankan;

/**
 * 游戏服务接口
 */
// 定义一个名为 GameService 的接口
public interface GameService {
    // 定义一个名为 start 的方法，用于开始游戏，没有参数，没有返回值
    public void start();
    // 定义一个名为 getPieces 的方法，用于获取游戏区域的所有游戏块，没有参数，返回一个二维数组
    public Piece[][] getPieces();
    // 定义一个名为 hasPieces 的方法，用于检查游戏区域是否还有游戏块，没有参数，返回一个布尔值
    public boolean hasPieces();
    // 定义一个名为 shuffle 的方法，用于重新排列游戏区域的所有游戏块，没有参数，没有返回值
    public void shuffle();
    // 定义一个名为 findPiece 的方法，用于根据触摸的坐标找到对应的游戏块，接收两个浮点数作为参数，返回一个 Piece 对象
    public Piece findPiece(float touchX, float touchY);
    // 定义一个名为 link 的方法，用于检查两个游戏块是否可以连接，接收两个 Piece 对象作为参数，返回一个 LinkInfo 对象
    public LinkInfo link(Piece p1, Piece p2);
}