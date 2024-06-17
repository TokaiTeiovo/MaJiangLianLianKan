package cn.edu.bistu.majianglianliankan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 接收器方法类
 * - 广播接收器
 */
// 定义一个名为 MyReceiver 的类，继承自 BroadcastReceiver 类
// BroadcastReceiver 是 Android 系统中的广播接收器，用于接收来自系统或应用发送的广播
public class MyReceiver extends BroadcastReceiver {

    // 重写 BroadcastReceiver 类的 onReceive 方法
    // 当接收到匹配的广播时，系统会调用这个方法
    @Override
    public void onReceive(Context context, Intent intent) {
        // 从 Intent 中获取名为 "name" 的字符串，这是发送广播时附带的数据
        String msg = intent.getStringExtra("name")+"，恭喜你！你只用了"+intent.getStringExtra("time")+"s";
        // 使用 Toast 显示消息，Toast 是 Android 系统中的一种简单反馈信息的方式，可以在屏幕上短暂显示一段文本
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}