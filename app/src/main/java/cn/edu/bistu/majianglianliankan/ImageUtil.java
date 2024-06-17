package cn.edu.bistu.majianglianliankan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 图标工具包类
 */
// 定义一个名为 ImageUtil 的类，用于处理图像相关的操作
public class ImageUtil {
    // 定义一个 List，用于存储所有的图像资源 ID
    private static List<Integer> imageValues = getImageValues();

    /**
     * 从本地调用麻将图标id，组成列表
     * @return 图标id列表
     */
    // 定义一个名为 getImageValues 的方法，用于获取所有的图像资源 ID
    public static List<Integer> getImageValues() {
        try {
            // 获取 R.drawable 类中的所有字段
            Field[] drawableFields = R.drawable.class.getFields();
            // 创建一个 List，用于存储所有的图像资源 ID
            List<Integer> resourceValues = new ArrayList<Integer>();
            // 遍历所有的字段
            for (Field field : drawableFields) {
                // 如果字段的名称包含 "m_"，则将其值添加到 List 中
                if (field.getName().contains("m_")) {
                    resourceValues.add(field.getInt(R.drawable.class));
                }
            }
            // 返回所有的图像资源 ID
            return resourceValues;
        }catch (Exception e){
            // 如果出现异常，打印错误信息，并返回 null
            System.out.println("image not found");
            return null;
        }
    }

    /**
     * 从指定id列表中随机取出指定数量的id组成新的列表
     * @param sourceValues  - 源id列表
     * @param size          - 数量
     * @return id列表
     */
    // 定义一个名为 getRandomValues 的方法，用于从指定的 List 中随机获取指定数量的元素
    public static List<Integer> getRandomValues(List<Integer> sourceValues, int size){
        // 创建一个 Random 对象
        Random random = new Random();
        // 创建一个 List，用于存储结果
        List<Integer> result = new ArrayList<Integer>();
        // 循环指定的次数
        for (int i = 0; i < size; i++) {
            try {
                // 随机获取一个索引
                int index = random.nextInt(sourceValues.size());
                // 获取该索引对应的元素，并添加到结果中
                Integer image = sourceValues.get(index);
                result.add(image);
            } catch (IndexOutOfBoundsException e) {
                // 如果出现索引越界异常，返回结果
                return result;
            }
        }
        // 返回结果
        return result;
    }

    /**
     * 获取指定数量的麻将图标
     * @param size  - 图标牌数量
     * @return 数量为size一半（向上取整）的图标列表
     */
    // 定义一个名为 getPlayValues 的方法，用于获取指定数量的图像资源 ID
    public static List<Integer> getPlayValues(int size){
        // 如果 size 是奇数，将其加 1
        if(size % 2 != 0){
            size += 1;
        }
        // 从所有的图像资源 ID 中随机获取 size / 2 个元素
        List<Integer> playImageValues = getRandomValues(imageValues, size / 2);
        // 将获取的元素复制一份，并添加到结果中
        playImageValues.addAll(playImageValues);
        // 打乱结果的顺序
        Collections.shuffle(playImageValues);
        // 返回结果
        return playImageValues;
    }

    /**
     *
     * @param context   - 游戏画面
     * @param size      - 游戏图标数量
     * @return 图片列表
     */
    // 定义一个名为 getPlayImages 的方法，用于获取指定数量的 PieceImage 对象
    public static List<PieceImage> getPlayImages(Context context, int size){
        // 获取指定数量的图像资源 ID
        List<Integer> resourceValues = getPlayValues(size);
        // 创建一个 List，用于存储结果
        List<PieceImage> result = new ArrayList<PieceImage>();
        // 遍历所有的图像资源 ID
        for (Integer value : resourceValues) {
            // 从资源中加载图像，并将其缩放到指定的大小
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), value);
            bitmap = Bitmap.createScaledBitmap(bitmap, GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT, true);
            // 创建一个 PieceImage 对象，并添加到结果中
            PieceImage pieceImage = new PieceImage(bitmap, value);
            result.add(pieceImage);
        }
        // 返回结果
        return result;
    }

    /**
     * 获取画面图片
     * @param context   - 游戏画面
     * @return 栅格化图片
     */
    // 定义一个名为 getSelectImage 的方法，用于获取选中的图像
    public static Bitmap getSelectImage(Context context){
        // 从资源中加载图像，并将其缩放到指定的大小
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.selected);
        bitmap = Bitmap.createScaledBitmap(bitmap, GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT, true);
        // 返回图像
        return bitmap;
    }
}
