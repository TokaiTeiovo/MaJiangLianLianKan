package cn.edu.bistu.majianglianliankan;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * 玩家信息方法类
 */
public class RankingActivity extends AppCompatActivity {
    // 定义一个 Ranking 类型的列表，用于存储玩家的排名信息
    private List<Ranking> mData = null;
    // 定义一个 RankingAdapter 对象，用于将排名信息显示在 ListView 中
    private RankingAdapter mAdapter = null;
    // 定义一个 ListView 对象，用于显示排名信息
    private ListView listView;
    // 定义一个 DataBaseHelper 对象，用于操作数据库
    private DataBaseHelper dataBaseHelper;
    // 定义一个 SQLiteDatabase 对象，用于执行 SQL 语句
    private SQLiteDatabase database;

    // 重写 AppCompatActivity 类的 onCreate 方法
    // 当 Activity 被创建时，系统会调用这个方法
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 调用父类的 onCreate 方法，完成基本的初始化工作
        super.onCreate(savedInstanceState);
        // 设置 Activity 的布局文件
        setContentView(R.layout.activity_ranking);
        // 通过 findViewById 方法，根据 ID 获取布局文件中的 ListView 组件
        listView = findViewById(R.id.list_view);
        // 创建一个 DataBaseHelper 对象，用于操作数据库
        dataBaseHelper = new DataBaseHelper(this,"ranking",null,1);
        // 获取可写的数据库
        database = dataBaseHelper.getWritableDatabase();
        // 查询 users 表，获取所有的用户信息
        Cursor cursor = database.query("users", null, null, null, null, null, "time");
        // 创建一个新的 LinkedList 对象，用于存储排名信息
        mData = new LinkedList<>();
        // 添加表头信息
        mData.add(new Ranking("名次","绰号","用时(s)","上榜时间"));
        int i = 1;
        // 遍历查询结果，获取每一行的数据
        if (cursor.moveToFirst()) {
            do {
                // 获取名次
                String id = String.valueOf(i++);
                // 获取绰号
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                // 获取用时
                String time = cursor.getString(2);
                // 获取上榜时间
                String date = cursor.getString(3);
                // 创建一个新的 Ranking 对象，将查询结果添加到列表中
                mData.add(new Ranking(id,name,time,date));
            } while (cursor.moveToNext());
        }
        // 关闭 Cursor，释放资源
        cursor.close();
        // 创建一个新的 RankingAdapter 对象，用于将排名信息显示在 ListView 中
        mAdapter = new RankingAdapter((LinkedList<Ranking>) mData, this);
        // 设置 ListView 的适配器
        listView.setAdapter(mAdapter);
    }
}
