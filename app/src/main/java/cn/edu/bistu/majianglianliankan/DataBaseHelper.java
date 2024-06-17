package cn.edu.bistu.majianglianliankan;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库连接方法类
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    // 定义构造函数，接收一个 Context，数据库名，CursorFactory 和版本号作为参数
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        // 调用父类的构造函数
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 执行 SQL 语句，创建一个名为 users 的表，包含 id，name，time 和 date 四个字段
        db.execSQL("create table users (id integer primary key,name varchar(50),time varchar(50),date varchar(50))");
    }

    // 重写 onUpgrade 方法，用于在数据库版本升级时执行
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 在这里添加数据库版本升级的代码
    }
}