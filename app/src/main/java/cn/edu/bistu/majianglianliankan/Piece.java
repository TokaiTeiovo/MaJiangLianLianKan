package cn.edu.bistu.majianglianliankan;

import android.graphics.Point;

/**
 * 麻将图标内部类
 */
public class Piece {
    // 定义一个 PieceImage 对象，表示麻将的图像
    private PieceImage pieceImage;
    // 定义一个整数，表示麻将的开始 X 坐标
    private int beginX;
    // 定义一个整数，表示麻将的开始 Y 坐标
    private int beginY;
    // 定义一个整数，表示麻将的索引 X 坐标
    private int indexX;
    // 定义一个整数，表示麻将的索引 Y 坐标
    private int indexY;

    public Piece(int indexX, int indexY){
        this.indexX = indexX;
        this.indexY = indexY;
    }

    public Point getCenter(){
        return new Point(getBeginX() + GameConf.PIECE_WIDTH / 2, getBeginY() + GameConf.PIECE_HEIGHT / 2);
    }

    public boolean isSameImage(Piece otherPieceImage){
        return pieceImage.getImageId() == otherPieceImage.getPieceImage().getImageId();
    }

    public PieceImage getPieceImage(){
        return pieceImage;
    }

    public void setPieceImage(PieceImage pieceImage){
        this.pieceImage = pieceImage;
    }

    public int getBeginX(){
        return beginX;
    }

    public void setBeginX(int beginX){
        this.beginX = beginX;
    }

    public int getBeginY(){
        return beginY;
    }

    public void setBeginY(int beginY){
        this.beginY = beginY;
    }

    public int getIndexX(){
        return indexX;
    }

    public void setIndexX(int indexX){
        this.indexX = indexX;
    }

    public int getIndexY(){
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }
}
