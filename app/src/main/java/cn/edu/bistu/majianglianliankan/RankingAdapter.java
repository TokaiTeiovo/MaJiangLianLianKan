package cn.edu.bistu.majianglianliankan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * 玩家信息转换器方法类
 */
public class RankingAdapter extends BaseAdapter {
    // 定义一个 Ranking 类型的 LinkedList，用于存储排名数据
    private LinkedList<Ranking> mData;
    // 定义一个 Context 对象，表示应用程序环境
    private Context mContext;

    // 定义一个构造函数，接收一个 Ranking 类型的 LinkedList 和一个 Context 作为参数
    public RankingAdapter(LinkedList<Ranking> mData, Context mContext) {
        // 设置排名数据
        this.mData = mData;
        // 设置应用程序环境
        this.mContext = mContext;
    }

    // 重写 BaseAdapter 类的 getCount 方法，用于获取列表的长度
    public int getCount() {
        // 返回排名数据的长度
        return mData.size();
    }

    // 重写 BaseAdapter 类的 getItem 方法，用于获取指定位置的数据
    public Object getItem(int position) {
        // 由于这个方法在这个类中没有被使用，所以返回 null
        return null;
    }

    // 重写 BaseAdapter 类的 getItemId 方法，用于获取指定位置的数据的 ID
    public long getItemId(int position) {
        // 在这个类中，数据的 ID 就是它的位置
        return position;
    }

    // 重写 BaseAdapter 类的 getView 方法，用于获取表示指定位置的数据的视图
    public View getView(int position, View convertView, ViewGroup parent) {
        // 使用 LayoutInflater 将 ranking_item 布局文件转换为 View 对象
        convertView = LayoutInflater.from(mContext).inflate(R.layout.ranking_item,null);
        // 通过 findViewById 方法，根据 ID 获取布局文件中的 TextView 组件
        TextView uid = convertView.findViewById(R.id.uid);
        TextView uname = convertView.findViewById(R.id.uname);
        TextView utime = convertView.findViewById(R.id.utime);
        TextView udate = convertView.findViewById(R.id.udate);
        // 设置 TextView 的文本为排名数据的 ID
        uid.setText(mData.get(position).getId());
        // 设置 TextView 的文本为排名数据的名字
        uname.setText(mData.get(position).getName());
        // 设置 TextView 的文本为排名数据的时间
        utime.setText(mData.get(position).getTime());
        // 设置 TextView 的文本为排名数据的日期
        udate.setText(mData.get(position).getDate());
        // 返回表示排名数据的视图
        return convertView;
    }
}