package cn.edu.bistu.majianglianliankan;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏区域方法类
 */
public class Board {
    /**
     * 创建游戏区域图标列表
     * - 可继承的私有方法
     * @param gameConf  - 游戏信息
     * @param pieces    - 麻将图标二维数组
     * @return 游戏区域图标列表
     */
    // 定义一个受保护的方法，用于创建游戏区域的图标列表
    protected List<Piece> createPieces(GameConf gameConf, Piece[][] pieces) {
        // 创建一个用于存放非空图标的列表
        List<Piece> notNullPieces = new ArrayList<Piece>();
        // 遍历二维数组 pieces
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                // 为每个位置创建一个 Piece 对象，并添加到列表中
                Piece piece = new Piece(i, j);
                notNullPieces.add(piece);
            }
        }
        // 返回非空图标列表
        return notNullPieces;
    }

    /**
     * 根据游戏信息创建麻将图标二维数组列
     * @param conf  - 游戏信息
     * @return 麻将图标二维数组
     */
    // 定义一个公共方法，用于根据游戏配置信息创建麻将图标的二维数组
    public Piece[][] create(GameConf conf){
        // 创建一个二维数组 pieces
        Piece[][] pieces = new Piece[conf.getXSize()][conf.getYSize()];
        // 调用 createPieces 方法，获取非空图标列表
        List<Piece> notNullPieces = createPieces(conf, pieces);
        // 获取游戏图标列表
        List<PieceImage> playImages = ImageUtil.getPlayImages(conf.getContext(), notNullPieces.size());
        // 获取图标的宽度和高度
        int imageWidth = GameConf.PIECE_WIDTH;
        int imageHeight = GameConf.PIECE_HEIGHT;
        // 遍历非空图标列表
        for (int i = 0; i < notNullPieces.size(); i++) {
            // 获取当前图标
            Piece piece = notNullPieces.get(i);
            // 设置当前图标的图像
            piece.setPieceImage(playImages.get(i));
            // 设置当前图标的开始坐标
            piece.setBeginX(piece.getIndexX() * imageWidth + conf.getBeginImageX());
            piece.setBeginY(piece.getIndexY() * imageHeight + conf.getBeginImageY());
            // 将当前图标添加到二维数组中
            pieces[piece.getIndexX()][piece.getIndexY()] = piece;
        }
        // 返回二维数组
        return pieces;
    }
}
