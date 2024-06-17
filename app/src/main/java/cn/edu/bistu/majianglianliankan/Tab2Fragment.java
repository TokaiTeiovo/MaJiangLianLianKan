package cn.edu.bistu.majianglianliankan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 帮助页面
 */
// 定义一个名为 Tab2Fragment 的类，继承自 Fragment 类
public class Tab2Fragment extends Fragment {
    // 重写 Fragment 类的 onCreateView 方法，用于创建和返回 Fragment 的视图
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // savedInstanceState 参数包含了之前保存的 Fragment 的状态，如果是首次创建，则为 null
        View view = inflater.inflate(R.layout.tab2, null);
        // 返回创建的视图
        return view;
    }
}
