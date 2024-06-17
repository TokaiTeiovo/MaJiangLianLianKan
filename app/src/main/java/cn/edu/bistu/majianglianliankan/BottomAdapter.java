package cn.edu.bistu.majianglianliankan;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 底层适配器内部类
 */
public class BottomAdapter extends FragmentStateAdapter {
    // 定义一个 List，用于存储 Fragment 对象
    private List<Fragment> fragments = new ArrayList<>();

    // 定义构造函数，接收一个 FragmentActivity 参数
    public BottomAdapter(FragmentActivity fa) {
        // 调用父类的构造函数
        super(fa);
    }

    // 重写 createFragment 方法，根据位置返回对应的 Fragment
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    // 重写 getItemCount 方法，返回 Fragment 的数量
    @Override
    public int getItemCount() {
        return fragments.size();
    }

    // 定义一个方法，用于添加 Fragment 到 List 中
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }
}