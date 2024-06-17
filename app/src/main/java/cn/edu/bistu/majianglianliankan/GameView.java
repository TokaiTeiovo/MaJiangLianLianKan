package cn.edu.bistu.majianglianliankan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * 游戏画面显示方法类
 */
// 定义一个名为 GameView 的类，继承自 View 类
public class GameView extends View {
    // 定义一个 GameService 对象，用于处理游戏逻辑
    private GameService gameService;
    // 定义一个 Piece 对象，表示当前选中的游戏块
    private Piece selectedPiece;
    // 定义一个 LinkInfo 对象，表示当前连接的线路
    private LinkInfo linkInfo;
    // 定义一个 Paint 对象，用于绘制图形和文本
    private Paint paint;
    // 定义一个 Bitmap 对象，表示选中的图像
    private Bitmap selectImage;

    // 定义构造函数，接收一个 Context 对象和一个 AttributeSet 对象作为参数
    public GameView(Context context, AttributeSet attrs) {
        // 调用父类的构造函数
        super(context, attrs);
        // 创建一个新的 Paint 对象
        this.paint = new Paint();
        // 设置 Paint 对象的颜色为红色
        this.paint.setColor(Color.RED);
        // 设置 Paint 对象的文本大小为 34
        this.paint.setTextSize(34);
        // 设置 Paint 对象的线宽为 3
        this.paint.setStrokeWidth(3);
    }

    // 定义一个名为 setSelectImage 的方法，用于设置选中的图像
    public void setSelectImage(Bitmap bitmap) {
        this.selectImage = bitmap;
    }

    // 定义一个名为 setGameService 的方法，用于设置 GameService 对象
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    // 定义一个名为 setSelectedPiece 的方法，用于设置选中的游戏块
    public void setSelectedPiece(Piece piece) {
        this.selectedPiece = piece;
    }

    // 定义一个名为 setLinkInfo 的方法，用于设置连接的线路
    public void setLinkInfo(LinkInfo linkInfo) {
        this.linkInfo = linkInfo;
    }

    /**
     * 绘制折线的调用方法
     * @param canvas    - 画布
     */
    @Override
    public void onDraw(Canvas canvas) {
        // 调用父类的 onDraw 方法
        super.onDraw(canvas);
        // 如果 GameService 对象为 null，直接返回
        if (this.gameService == null) {
            return;
        }
        // 获取所有的游戏块
        Piece[][] pieces = gameService.getPieces();
        // 如果游戏块不为 null
        if (pieces != null) {
            // 遍历所有的游戏块
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    // 如果当前游戏块不为 null
                    if (pieces[i][j] != null) {
                        // 获取当前游戏块
                        Piece piece = pieces[i][j];
                        // 获取当前游戏块的图像
                        Bitmap bm = piece.getPieceImage().getImage();
                        // 在画布上绘制当前游戏块的图像
                        canvas.drawBitmap(bm,piece.getBeginX(),piece.getBeginY(),null);
                    }
                }
            }
        }
        // 如果连接的线路不为 null
        if (this.linkInfo != null) {
            // 绘制连接的线路
            drawLine(this.linkInfo, canvas);
            // 将连接的线路设置为 null
            this.linkInfo = null;
        }
        // 如果选中的游戏块不为 null
        if (this.selectedPiece != null) {
            // 在画布上绘制选中的图像
            canvas.drawBitmap(this.selectImage, this.selectedPiece.getBeginX(),
                    this.selectedPiece.getBeginY(), null);
        }
    }

    /**
     * 绘制图标连接的折线
     * @param linkInfo  - 绘制线段的节点
     * @param canvas    - 画布
     */
    // 定义一个名为 drawLine 的方法，用于绘制连接的线路
    private void drawLine(LinkInfo linkInfo, Canvas canvas) {
        // 获取连接的线路的所有点
        List<Point> points = linkInfo.getLinkPoints();
        // 遍历所有的点
        for (int i = 0; i < points.size() - 1; i++) {
            // 获取当前点和下一个点
            Point currentPoint = points.get(i);
            Point nextPoint = points.get(i + 1);
            // 在画布上绘制一条从当前点到下一个点的线
            canvas.drawLine(currentPoint.x, currentPoint.y, nextPoint.x,
                    nextPoint.y, this.paint);
        }
    }

    /**
     * 游戏继续
     */
    // 定义一个名为 startGame 的方法，用于开始游戏
    public void startGame(){
        // 调用 GameService 对象的 start 方法，开始游戏
        this.gameService.start();
        // 使视图无效，导致视图重新绘制
        this.postInvalidate();
    }
}
