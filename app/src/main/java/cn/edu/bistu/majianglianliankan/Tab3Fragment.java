package cn.edu.bistu.majianglianliankan;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 设置页面
 */
// 定义一个名为 Tab3Fragment 的类，继承自 Fragment 类
public class Tab3Fragment extends Fragment {
    // 定义一个 MediaPlayer 对象，用于播放音乐
    private MediaPlayer mediaPlayer;
    // 定义一个 SharedPreferences 对象，用于存储设置
    private SharedPreferences sharedPreferences;
    // 定义一个 SharedPreferences.Editor 对象，用于编辑设置
    private SharedPreferences.Editor editor;

    // 重写 Fragment 类的 onCreateView 方法，用于创建和返回 Fragment 的视图
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 使用 LayoutInflater 将 tab3 布局文件转换为 View 对象
        View view = inflater.inflate(R.layout.tab3, null);
        // 获取 MainActivity 实例
        final MainActivity mainActivity = (MainActivity) getActivity();
        // 获取 MainActivity 中的 mediaPlayer 对象
        mediaPlayer = mainActivity.mediaPlayer;
        // 获取存储设置的 SharedPreferences 对象
        sharedPreferences = getContext().getSharedPreferences("config", getContext().MODE_PRIVATE);
        // 通过 findViewById 方法，根据 ID 获取布局文件中的 Switch 组件
        Switch switch1 = view.findViewById(R.id.switch1);
        // 设置 switch1 的状态为存储的 "music" 设置
        switch1.setChecked(sharedPreferences.getBoolean("music", true));
        // 为 switch1 设置状态改变的监听器
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 获取 SharedPreferences.Editor 对象
                editor = sharedPreferences.edit();
                // 如果 switch1 被按下
                if (buttonView.isPressed()) {
                    // 如果 switch1 的状态为选中
                    if (isChecked) {
                        // 开始播放音乐
                        mediaPlayer.start();
                        // 将 "music" 设置为 true
                        editor.putBoolean("music",true);
                        // 显示 "打开音乐" 的 Toast 消息
                        Toast.makeText(getContext(), "打开音乐", Toast.LENGTH_SHORT).show();
                    } else {
                        // 暂停播放音乐
                        mediaPlayer.pause();
                        // 将音乐的播放位置设置为开始
                        mediaPlayer.seekTo(0);
                        // 将 "music" 设置为 false
                        editor.putBoolean("music",false);
                        // 显示 "关闭音乐" 的 Toast 消息
                        Toast.makeText(getContext(), "关闭音乐", Toast.LENGTH_SHORT).show();
                    }
                }
                // 提交设置的更改
                editor.commit();
            }
        });
        // 通过 findViewById 方法，根据 ID 获取布局文件中的 Switch 组件
        Switch switch2 = view.findViewById(R.id.switch2);
        // 设置 switch2 的状态为存储的 "sound" 设置
        switch2.setChecked(sharedPreferences.getBoolean("sound",true));
        // 为 switch2 设置状态改变的监听器
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 获取 SharedPreferences.Editor 对象
                editor = sharedPreferences.edit();
                // 如果 switch2 被按下
                if(buttonView.isPressed()) {
                    // 如果 switch2 的状态为选中
                    if (isChecked) {
                        // 将 mainActivity 的 sound 属性设置为 true
                        mainActivity.sound = true;
                        // 将 "sound" 设置为 true
                        editor.putBoolean("sound",true);
                        // 显示 "打开音效" 的 Toast 消息
                        Toast.makeText(getContext(), "打开音效", Toast.LENGTH_SHORT).show();
                    } else {
                        // 将 mainActivity 的 sound 属性设置为 false
                        mainActivity.sound = false;
                        // 将 "sound" 设置为 false
                        editor.putBoolean("sound",false);
                        // 显示 "关闭音效" 的 Toast 消息
                        Toast.makeText(getContext(), "关闭音效", Toast.LENGTH_SHORT).show();
                    }
                }
                // 提交设置的更改
                editor.commit();
            }
        });
        // 返回创建的视图
        return view;
    }
}
