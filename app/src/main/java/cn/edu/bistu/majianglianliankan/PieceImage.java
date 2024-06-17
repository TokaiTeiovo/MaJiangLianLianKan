package cn.edu.bistu.majianglianliankan;

import android.graphics.Bitmap;

/**
 * 图标图片内部类
 */
public class PieceImage {
    // 定义一个 Bitmap 对象，表示图像
    private Bitmap image;
    // 定义一个整数，表示图像的 ID
    private int imageId;
    public PieceImage(Bitmap image, int imageId) {
        super();
        this.image = image;
        this.imageId = imageId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
