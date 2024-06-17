package cn.edu.bistu.majianglianliankan;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏服务方法类
 * 继承自游戏服务接口
 */
// 定义一个名为 GameServiceImpl 的类，它实现了 GameService 接口
public class GameServiceImpl implements GameService{
    // 定义一个二维数组，用于存储游戏区域的所有游戏块
    private Piece[][] pieces;
    // 定义一个 GameConf 对象，用于存储游戏的配置信息
    private GameConf gameConf;

    // 定义构造函数，接收一个 GameConf 对象作为参数
    public GameServiceImpl(GameConf gameConf) {
        // 初始化 gameConf
        this.gameConf = gameConf;
    }

    // 实现 GameService 接口的 start 方法，用于开始游戏
    @Override
    public void start() {
        // 创建一个 Board 对象
        Board board = new Board();
        // 调用 Board 对象的 create 方法，生成游戏区域的所有游戏块，并存储到 pieces 中
        this.pieces = board.create(gameConf);
    }

    // 实现 GameService 接口的 getPieces 方法，用于获取游戏区域的所有游戏块
    @Override
    public Piece[][] getPieces() {
        // 返回 pieces
        return pieces;
    }

    // 定义一个方法，用于重新排列游戏区域的所有游戏块
    public void shuffle() {
        // 创建一个 List，用于存储所有的游戏块
        List<Piece> piecesList = new ArrayList<>();
        // 遍历二维数组 pieces
        for(int i = 0; i < pieces.length; i++){
            for(int j = 0; j < pieces[i].length; j++){
                // 如果当前位置的游戏块不为空，则添加到 piecesList 中
                if (pieces[i][j] != null){
                    piecesList.add(pieces[i][j]);
                }else {
                    // 如果当前位置的游戏块为空，则在 piecesList 中添加一个 null
                    piecesList.add(null);
                }
            }
        }

        // 调用 Collections 的 shuffle 方法，随机排列 piecesList 中的元素
        Collections.shuffle(piecesList);
        // 定义一个变量，用于记录 piecesList 中的元素的索引
        int index = 0;
        // 遍历二维数组 pieces
        for (int i=0;i<pieces.length;i++){
            for (int j=0;j<pieces[i].length;j++){
                // 将 piecesList 中的元素依次赋值给 pieces 中的元素
                pieces[i][j] = piecesList.get(index++);
                // 如果当前位置的游戏块不为空，则设置其坐标和开始坐标
                if(pieces[i][j] != null){
                    pieces[i][j].setIndexX(i);
                    pieces[i][j].setIndexY(j);
                    pieces[i][j].setBeginX(pieces[i][j].getIndexX() * GameConf.PIECE_WIDTH + gameConf.getBeginImageX());
                    pieces[i][j].setBeginY(pieces[i][j].getIndexY() * GameConf.PIECE_HEIGHT + gameConf.getBeginImageY());
                }
            }
        }
    }

    // 实现 GameService 接口的 hasPieces 方法，用于检查游戏区域是否还有游戏块
    @Override
    public boolean hasPieces() {
        // 遍历二维数组 pieces
        for (int i = 0; i < pieces.length; i++){
            for (int j = 0; j < pieces[i].length; j++){
                // 如果当前位置的游戏块不为空，则返回 true
                if (pieces[i][j] != null){
                    return true;
                }
            }
        }
        // 如果所有位置的游戏块都为空，则返回 false
        return false;
    }

    // 实现 GameService 接口的 findPiece 方法，用于根据触摸的坐标找到对应的游戏块
    @Override
    public Piece findPiece(float touchX, float touchY) {
        // 计算触摸点相对于游戏区域的开始坐标的坐标
        int relativeX = (int) (touchX - this.gameConf.getBeginImageX());
        int relativeY = (int) (touchY - this.gameConf.getBeginImageY());

        // 如果触摸点在游戏区域的开始坐标的左边或上边，则返回 null
        if (relativeX < 0 || relativeY < 0){
            return null;
        }

        // 计算触摸点在二维数组 pieces 中的索引
        int indexX = getIndex(relativeX, GameConf.PIECE_WIDTH);
        int indexY = getIndex(relativeY, GameConf.PIECE_HEIGHT);

        // 如果触摸点在二维数组 pieces 的边界之外，则返回 null
        if(indexX < 0 || indexY < 0){
            return null;
        }

        // 如果触摸点在二维数组 pieces 的边界之外，则返回 null
        if(indexX >= gameConf.getXSize() || indexY >= gameConf.getYSize()){
            return null;
        }

        // 返回二维数组 pieces 中对应位置的游戏块
        return this.pieces[indexX][indexY];
    }

    // 定义一个方法，用于计算触摸点在二维数组 pieces 中的索引
    private int getIndex(int relative, int size){
        // 定义一个变量，用于存储索引
        int index = -1;
        // 如果 relative 能被 size 整除，则索引为 relative 除以 size 减 1
        if(relative % size == 0){
            index = relative / size - 1;
        }else{
            // 如果 relative 不能被 size 整除，则索引为 relative 除以 size
            index = relative / size;
        }
        // 返回索引
        return index;
    }

    // 实现 GameService 接口的 link 方法，用于检查两个游戏块是否可以连接
    @Override
    public LinkInfo link(Piece p1, Piece p2) {
        // 如果两个游戏块是同一个游戏块，则返回 null
        if (p1.equals(p2)){
            return null;
        }

        // 如果两个游戏块的图像不同，则返回 null
        if (!p1.isSameImage(p2)){
            return null;
        }

        // 如果第一个游戏块在第二个游戏块的左边，则交换两个游戏块的位置
        if (p2.getIndexX() < p1.getIndexX()){
            return link(p2, p1);
        }

        // 获取两个游戏块的中心点
        Point p1Point = p1.getCenter();
        Point p2Point = p2.getCenter();

        // 如果两个游戏块在同一行
        if(p1.getIndexY() == p2.getIndexY()){
            // 如果两个游戏块之间没有其他游戏块，则它们可以连接
            if(!isXBlock(p1Point, p2Point, GameConf.PIECE_WIDTH)){
                return new LinkInfo(p1Point, p2Point);
            }
        }

        // 如果两个游戏块在同一列
        if(p1.getIndexX() == p2.getIndexX()){
            // 如果两个游戏块之间没有其他游戏块，则它们可以连接
            if(!isYBlock(p1Point, p2Point, GameConf.PIECE_HEIGHT)){
                return new LinkInfo(p1Point, p2Point);
            }
        }

        // 获取两个游戏块的拐点
        Point cornerPoint = getCornerPoint(p1Point, p2Point, GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT);

        // 如果两个游戏块有一个拐点，则它们可以连接
        if (cornerPoint != null){
            return new LinkInfo(p1Point, cornerPoint, p2Point);
        }

        // 获取两个游戏块的所有可能的连接点
        Map<Point, Point> turns = getLinkPoints(p1Point, p2Point, GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT);

        // 如果两个游戏块有两个拐点，则它们可以连接
        if(turns.size() != 0){
            return getShortcut(p1Point, p2Point, turns, getDistance(p1Point, p2Point));
        }
        // 如果两个游戏块不能连接，则返回 null
        return null;
    }

    // 定义一个名为 getLinkPoints 的方法，用于获取两个游戏块的所有可能的连接点
    private Map<Point, Point> getLinkPoints(Point point1, Point point2, int pieceWidth, int pieceHeight){
        // 创建一个 Map，用于存储所有可能的连接点
        Map<Point, Point> result = new HashMap<Point, Point>();

        // 获取第一个游戏块的上方和下方的通道
        List<Point> p1UpChanel = getUpChanel(point1, point2.y, pieceHeight);
        List<Point> p1DownChanel = getDownChanel(point1, point2.y, pieceHeight);

        // 获取第一个游戏块的右方的通道
        List<Point> p1RightChanel = getLeftChanel(point1, point2.x, pieceWidth);

        // 获取第二个游戏块的下方和上方的通道
        List<Point> p2DownChanel = getDownChanel(point2, point1.y, pieceHeight);
        List<Point> p2UpChanel = getUpChanel(point2, point1.y, pieceHeight);

        // 获取第二个游戏块的左方的通道
        List<Point> p2LeftChanel = getLeftChanel(point2, point1.x, pieceWidth);

        // 获取游戏区域的高度和宽度的最大值
        int heightMax = (this.gameConf.getYSize() + 1) * pieceHeight + this.gameConf.getBeginImageY();
        int widthMax = (this.gameConf.getXSize() + 1) * pieceWidth + this.gameConf.getBeginImageX();

        // 如果第一个游戏块在第二个游戏块的左上方或左下方
        if(isLeftUp(point1, point2) || isLeftDown(point1, point2)){
            // 交换两个游戏块的位置，并递归调用 getLinkPoints 方法
            return getLinkPoints(point2, point1, pieceWidth, pieceHeight);
        }

        // 如果两个游戏块在同一行
        if(point1.y == point2.y){
            // 获取第一个游戏块和第二个游戏块上方的通道
            p1UpChanel = getUpChanel(point1, 0, pieceHeight);
            p2UpChanel = getUpChanel(point2, 0, pieceHeight);

            // 获取两个游戏块上方通道的连接点
            Map<Point, Point> upLinkPoints = getXLinkPoints(p1UpChanel, p2UpChanel, pieceHeight);

            // 获取第一个游戏块和第二个游戏块下方的通道
            p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
            p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);

            // 获取两个游戏块下方通道的连接点
            Map<Point, Point> downLinkPoints = getXLinkPoints(p1DownChanel, p2DownChanel, pieceHeight);

            // 将上方和下方的连接点都添加到结果中
            result.putAll(upLinkPoints);
            result.putAll(downLinkPoints);
        }

        // 如果两个游戏块在同一列
        if(point1.x == point2.x){
            // 获取第一个游戏块和第二个游戏块左方的通道
            List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
            p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);

            // 获取两个游戏块左方通道的连接点
            Map<Point, Point> leftLinkPoints = getYLinkPoints(p1LeftChanel, p2LeftChanel, pieceWidth);

            // 获取第一个游戏块和第二个游戏块右方的通道
            p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
            List<Point> p2RightChanel = getRightChanel(point2, widthMax, pieceWidth);

            // 获取两个游戏块右方通道的连接点
            Map<Point, Point> rightLinkPoints = getYLinkPoints(p1RightChanel, p2RightChanel, pieceWidth);

            // 将左方和右方的连接点都添加到结果中
            result.putAll(leftLinkPoints);
            result.putAll(rightLinkPoints);
        }

        // 如果第一个游戏块在第二个游戏块的右上方
        if(isRightUp(point1, point2)){
            // 获取第一个游戏块右方和上方的通道与第二个游戏块下方和左方的通道的连接点
            Map<Point, Point> upDownLinkPoints = getXLinkPoints(p1UpChanel, p2DownChanel, pieceWidth);
            Map<Point, Point> rightLeftLinkPoints = getYLinkPoints(p1RightChanel, p2LeftChanel, pieceHeight);

            // 获取第一个游戏块和第二个游戏块上方的通道
            p1UpChanel = getUpChanel(point1, 0, pieceHeight);
            p2UpChanel = getUpChanel(point2, 0, pieceHeight);

            // 获取两个游戏块上方通道的连接点
            Map<Point, Point> upUpLinkPoints = getXLinkPoints(p1UpChanel, p2UpChanel, pieceHeight);

            // 获取第一个游戏块和第二个游戏块下方的通道
            p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
            p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);

            // 获取两个游戏块下方通道的连接点
            Map<Point, Point> downDownLinkPoints = getXLinkPoints(p1DownChanel, p2DownChanel, pieceHeight);

            // 获取第一个游戏块和第二个游戏块右方的通道
            p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
            List<Point> p2RightChanel = getRightChanel(point2, widthMax, pieceWidth);

            // 获取两个游戏块右方通道的连接点
            Map<Point, Point> rightRightLinkPoints = getYLinkPoints(p1RightChanel, p2RightChanel, pieceHeight);

            // 获取第一个游戏块和第二个游戏块左方的通道
            List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
            p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);

            // 获取两个游戏块左方通道的连接点
            Map<Point, Point> leftLeftLinkPoints = getYLinkPoints(p1LeftChanel, p2LeftChanel, pieceHeight);

            // 将所有的连接点都添加到结果中
            result.putAll(upDownLinkPoints);
            result.putAll(rightLeftLinkPoints);
            result.putAll(upUpLinkPoints);
            result.putAll(downDownLinkPoints);
            result.putAll(rightRightLinkPoints);
            result.putAll(leftLeftLinkPoints);
        }

        // 如果第一个游戏块在第二个游戏块的右下方
        if(isRightDown(point1, point2)){
            // 获取第一个游戏块下方和右方的通道与第二个游戏块上方和左方的通道的连接点
            Map<Point, Point> downUpLinkPoints = getXLinkPoints(p1DownChanel, p2UpChanel, pieceWidth);
            Map<Point, Point> rightLeftLinkPoints = getYLinkPoints(p1RightChanel, p2LeftChanel, pieceHeight);

            // 获取第一个游戏块和第二个游戏块上方的通道
            p1UpChanel = getUpChanel(point1, 0, pieceHeight);
            p2UpChanel = getUpChanel(point2, 0, pieceHeight);

            // 获取两个游戏块上方通道的连接点
            Map<Point, Point> upUpLinkPoints = getXLinkPoints(p1UpChanel, p2UpChanel, pieceWidth);

            // 获取第一个游戏块和第二个游戏块下方的通道
            p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
            p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);

            // 获取两个游戏块下方通道的连接点
            Map<Point, Point> downDownLinkPoints = getXLinkPoints(p1DownChanel, p2DownChanel, pieceWidth);

            // 获取第一个游戏块和第二个游戏块左方的通道
            List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
            p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);

            // 获取两个游戏块左方通道的连接点
            Map<Point, Point> leftLeftLinkPoints = getYLinkPoints(p1LeftChanel, p2LeftChanel, pieceHeight);

            // 获取第一个游戏块和第二个游戏块右方的通道
            p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
            List<Point> p2RightChanel = getRightChanel(point2, widthMax, pieceWidth);

            // 获取两个游戏块右侧通道的连接点
            Map<Point, Point> rightRightLinkPoints = getYLinkPoints(p1RightChanel, p2RightChanel, pieceHeight);

            // 将所有的连接点都添加到结果中
            result.putAll(downUpLinkPoints);
            result.putAll(rightLeftLinkPoints);
            result.putAll(upUpLinkPoints);
            result.putAll(downDownLinkPoints);
            result.putAll(leftLeftLinkPoints);
            result.putAll(rightRightLinkPoints);
        }
        return result;
    }

    // 定义一个名为 getShortcut 的方法，用于获取两个点之间的最短路径
    private LinkInfo getShortcut(Point p1, Point p2, Map<Point, Point> turns, int shortDistance){
        // 创建一个 LinkInfo 列表，用于存储所有可能的连接路径
        List<LinkInfo> infos = new ArrayList<LinkInfo>();
        // 遍历 turns 中的所有点
        for(Point point1:turns.keySet()){
            // 获取当前点对应的另一个点
            Point point2 = turns.get(point1);
            // 创建一个新的 LinkInfo 对象，表示从 p1 到 point1，再到 point2，最后到 p2 的连接路径
            // 并将这个对象添加到 infos 列表中
            infos.add(new LinkInfo(p1, point1, point2, p2));
        }
        // 调用 getShortcut 方法，获取 infos 列表中的最短路径
        return getShortcut(infos, shortDistance);
    }

    // 定义一个名为 getShortcut 的方法，用于获取一个连接路径列表中的最短路径
    private LinkInfo getShortcut(List<LinkInfo> infos, int shortDistance){
        // 定义一个变量，用于存储当前最短路径的长度
        int temp1 = 0;
        // 定义一个变量，用于存储当前最短的连接路径
        LinkInfo result = null;
        // 遍历 infos 列表中的所有连接路径
        for (int i=0; i<infos.size(); i++){
            // 获取当前连接路径
            LinkInfo info = infos.get(i);
            // 计算当前连接路径的长度
            int distance = countAll(info.getLinkPoints());
            // 如果这是第一个连接路径，或者当前连接路径的长度小于已知的最短路径的长度
            if(i == 0 || distance - shortDistance < temp1){
                // 更新最短路径的长度和最短连接路径
                temp1 = distance - shortDistance;
                result = info;
            }
        }
        // 返回最短的连接路径
        return result;
    }

    // 定义一个名为 countAll 的方法，用于计算一个连接路径的长度
    private int countAll(List<Point> points){
        // 定义一个变量，用于存储连接路径的长度
        int result = 0;
        // 遍历连接路径中的所有点
        for(int i=0; i<points.size() - 1; i++){
            // 获取当前点和下一个点
            Point point1 = points.get(i);
            Point point2 = points.get(i + 1);
            // 计算当前点和下一个点之间的距离，并累加到连接路径的长度中
            result += getDistance(point1, point2);
        }
        // 返回连接路径的长度
        return result;
    }

    // 定义一个名为 getDistance 的方法，用于计算两个点之间的距离
    private int getDistance(Point p1, Point p2){
        // 计算两个点的 x 坐标和 y 坐标的差的平方和，然后取平方根，得到两个点之间的距离
        // 注意，这里假设每个单位的距离都是 1，所以实际上返回的是两个点的坐标差
        return (int) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    // 定义一个名为 getYLinkPoints 的方法，用于获取两个游戏块在 Y 轴方向上的所有可能的连接点
    private Map<Point, Point> getYLinkPoints(List<Point> p1Chanel, List<Point> p2Chanel, int pieceWidth){
        // 创建一个 Map，用于存储所有可能的连接点
        Map<Point, Point> result = new HashMap<Point, Point>();
        // 遍历第一个游戏块的通道中的所有点
        for(int i=0; i< p1Chanel.size(); i++){
            // 获取当前点
            Point temp1 = p1Chanel.get(i);
            // 遍历第二个游戏块的通道中的所有点
            for(int j=0; j< p2Chanel.size(); j++){
                // 获取当前点
                Point temp2 = p2Chanel.get(j);
                // 如果两个点的 x 坐标相同
                if(temp1.x == temp2.x){
                    // 如果两个点之间没有其他游戏块，则将这两个点作为一个可能的连接点添加到结果中
                    if(!isYBlock(temp1, temp2, pieceWidth)){
                        result.put(temp1, temp2);
                    }
                }
            }
        }
        // 返回所有可能的连接点
        return result;
    }

    // 定义一个名为 getXLinkPoints 的方法，用于获取两个游戏块在 X 轴方向上的所有可能的连接点
    private Map<Point, Point> getXLinkPoints(List<Point> p1Chanel, List<Point> p2Chanel, int pieceWidth){
        // 创建一个 Map，用于存储所有可能的连接点
        Map<Point, Point> result = new HashMap<Point, Point>();
        // 遍历第一个游戏块的通道中的所有点
        for(int i=0; i< p1Chanel.size(); i++){
            // 获取当前点
            Point temp1 = p1Chanel.get(i);
            // 遍历第二个游戏块的通道中的所有点
            for(int j=0; j< p2Chanel.size(); j++){
                // 获取当前点
                Point temp2 = p2Chanel.get(j);
                // 如果两个点的 y 坐标相同
                if(temp1.y == temp2.y){
                    // 如果两个点之间没有其他游戏块，则将这两个点作为一个可能的连接点添加到结果中
                    if(!isXBlock(temp1, temp2, pieceWidth)){
                        result.put(temp1, temp2);
                    }
                }
            }
        }
        // 返回所有可能的连接点
        return result;
    }

    // 定义一个名为 isLeftUp 的方法，用于判断第一个点是否在第二个点的左上方
    private boolean isLeftUp(Point p1, Point p2){
        return (p1.x > p2.x && p1.y > p2.y);
    }

    // 定义一个名为 isLeftDown 的方法，用于判断第一个点是否在第二个点的左下方
    private boolean isLeftDown(Point p1, Point p2){
        return (p1.x > p2.x && p1.y < p2.y);
    }

    // 定义一个名为 isRightUp 的方法，用于判断第一个点是否在第二个点的右上方
    private boolean isRightUp(Point p1, Point p2){
        return (p1.x < p2.x && p1.y > p2.y);
    }

    // 定义一个名为 isRightDown 的方法，用于判断第一个点是否在第二个点的右下方
    private boolean isRightDown(Point p1, Point p2){
        return (p1.x < p2.x && p1.y < p2.y);
    }

    // 定义一个名为 getCornerPoint 的方法，用于获取两个点之间的拐点
    private Point getCornerPoint(Point point1, Point point2, int pieceWidth, int pieceHeight){
        // 如果第一个点在第二个点的左上方或左下方，递归调用 getCornerPoint 方法
        if(isLeftUp(point1, point2) || isLeftDown(point1, point2)){
            return getCornerPoint(point1, point2, pieceWidth, pieceHeight);
        }

        // 获取第一个点右侧和上下两侧的通道
        List<Point> point1RightChanel = getRightChanel(point1, point2.x, pieceWidth);
        List<Point> point1UpChanel = getUpChanel(point1, point2.y, pieceHeight);
        List<Point> point1DownChanel = getDownChanel(point1, point2.y, pieceHeight);

        // 获取第二个点下侧和左侧两侧的通道
        List<Point> point2DownChanel = getDownChanel(point2, point1.y, pieceHeight);
        List<Point> point2LeftChanel = getLeftChanel(point2, point1.x, pieceWidth);
        List<Point> point2UpChanel = getUpChanel(point2, point1.y, pieceHeight);

        // 如果第一个点在第二个点的右上方
        if(isRightUp(point1, point2)){
            // 获取第一个点右侧通道和第二个点下侧通道的交点
            Point linkPoint1 = getWrapPoint(point1RightChanel, point2DownChanel);
            // 获取第一个点上侧通道和第二个点左侧通道的交点
            Point linkPoint2 = getWrapPoint(point1UpChanel, point2LeftChanel);
            // 返回两个交点中的一个，如果 linkPoint1 为 null，则返回 linkPoint2
            return (linkPoint1 == null) ? linkPoint2 : linkPoint1;
        }

        // 如果第一个点在第二个点的右下方
        if(isRightDown(point1, point2)){
            // 获取第一个点下侧通道和第二个点左侧通道的交点
            Point linkPoint1 = getWrapPoint(point1DownChanel, point2LeftChanel);
            // 获取第一个点右侧通道和第二个点上侧通道的交点
            Point linkPoint2 = getWrapPoint(point1RightChanel, point2UpChanel);
            // 返回两个交点中的一个，如果 linkPoint1 为 null，则返回 linkPoint2
            return (linkPoint1 == null) ? linkPoint2 : linkPoint1;
        }
        // 如果两个点之间没有拐点，返回 null
        return null;
    }

    // 定义一个名为 getWrapPoint 的方法，用于获取两个通道的交点
    private Point getWrapPoint(List<Point> p1Chanel, List<Point> p2Chanel){
        // 遍历第一个通道中的所有点
        for(int i=0; i<p1Chanel.size(); i++){
            Point temp1 = p1Chanel.get(i);
            // 遍历第二个通道中的所有点
            for(int j=0; j<p2Chanel.size(); j++){
                Point temp2 = p2Chanel.get(j);
                // 如果两个点相同，返回这个点
                if(temp1.equals(temp2)){
                    return temp1;
                }
            }
        }
        // 如果两个通道没有交点，返回 null
        return null;
    }

    // 定义一个名为 isXBlock 的方法，用于判断两个点在 X 轴方向上是否被其他游戏块阻挡
    private boolean isXBlock(Point p1, Point p2, int pieceWidth){
        // 如果第二个点在第一个点的左侧，交换两个点的位置
        if(p2.x < p1.x){
            return isXBlock(p2, p1, pieceWidth);
        }
        // 遍历两个点之间的所有点
        for(int i = p1.x + pieceWidth; i < p2.x; i = i+pieceWidth){
            // 如果这个点有游戏块，返回 true
            if(hasPiece(i, p1.y)){
                return true;
            }
        }
        // 如果两个点之间没有游戏块，返回 false
        return false;
    }

    // 定义一个名为 isYBlock 的方法，用于判断两个点在 Y 轴方向上是否被其他游戏块阻挡
    private boolean isYBlock(Point p1, Point p2, int pieceHeight){
        // 如果第二个点在第一个点的上方，交换两个点的位置
        if(p2.y < p1.y){
            return isYBlock(p2, p1, pieceHeight);
        }
        // 遍历两个点之间的所有点
        for(int i = p1.y + pieceHeight; i < p2.y; i = i + pieceHeight){
            // 如果这个点有游戏块，返回 true
            if(hasPiece(p1.x, i)){
                return true;
            }
        }
        // 如果两个点之间没有游戏块，返回 false
        return false;
    }

    // 定义一个名为 hasPiece 的方法，用于判断一个点是否有游戏块
    private boolean hasPiece(int x, int y){
        // 如果这个点没有游戏块，返回 false
        if(findPiece(x, y) == null)
            return false;
        // 如果这个点有游戏块，返回 true
        return true;
    }

    // 定义一个名为 getLeftChanel 的方法，用于获取一个点左侧的所有通道
    private List<Point> getLeftChanel(Point p, int min, int pieceWidth) {
        // 创建一个 List，用于存储所有的通道
        List<Point> result = new ArrayList<Point>();
        // 遍历这个点左侧的所有点
        for (int i = p.x - pieceWidth; i >= min; i = i - pieceWidth) {
            // 如果这个点有游戏块，返回已经找到的通道
            if (hasPiece(i, p.y)) {
                return result;
            }
            // 如果这个点没有游戏块，将这个点添加到通道中
            result.add(new Point(i, p.y));
        }
        // 返回所有的通道
        return result;
    }

    // 定义一个名为 getRightChanel 的方法，用于获取一个点右侧的所有通道
    private List<Point> getRightChanel(Point p, int max, int pieceWidth) {
        // 创建一个 List，用于存储所有的通道
        List<Point> result = new ArrayList<Point>();
        // 遍历这个点右侧的所有点
        for (int i = p.x + pieceWidth; i <= max; i = i + pieceWidth) {
            // 如果这个点有游戏块，返回已经找到的通道
            if (hasPiece(i, p.y)) {
                return result;
            }
            // 如果这个点没有游戏块，将这个点添加到通道中
            result.add(new Point(i, p.y));
        }
        // 返回所有的通道
        return result;
    }

    // 定义一个名为 getUpChanel 的方法，用于获取一个点上方的所有通道
    private List<Point> getUpChanel(Point p, int min, int pieceHeight) {
        // 创建一个 List，用于存储所有的通道
        List<Point> result = new ArrayList<Point>();
        // 遍历这个点上方的所有点
        for (int i = p.y - pieceHeight; i >= min; i = i - pieceHeight) {
            // 如果这个点有游戏块，返回已经找到的通道
            if (hasPiece(p.x, i)) {
                return result;
            }
            // 如果这个点没有游戏块，将这个点添加到通道中
            result.add(new Point(p.x, i));
        }
        // 返回所有的通道
        return result;
    }

    // 定义一个名为 getDownChanel 的方法，用于获取一个点下方的所有通道
    private List<Point> getDownChanel(Point p, int max, int pieceHeight) {
        // 创建一个 List，用于存储所有的通道
        List<Point> result = new ArrayList<Point>();
        // 遍历这个点下方的所有点
        for (int i = p.y + pieceHeight; i <= max; i = i + pieceHeight) {
            // 如果这个点有游戏块，返回已经找到的通道
            if (hasPiece(p.x, i)) {
                return result;
            }
            // 如果这个点没有游戏块，将这个点添加到通道中
            result.add(new Point(p.x, i));
        }
        // 返回所有的通道
        return result;
    }
}