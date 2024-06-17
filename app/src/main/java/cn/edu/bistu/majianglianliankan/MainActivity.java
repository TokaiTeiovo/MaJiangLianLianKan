package cn.edu.bistu.majianglianliankan;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// 定义一个名为 MainActivity 的类，继承自 AppCompatActivity 类
public class MainActivity extends AppCompatActivity {

    // 定义一个 BottomNavigationView 对象，用于显示底部导航栏
    private BottomNavigationView bottomNavigationView;
    // 定义一个 ViewPager2 对象，用于实现页面滑动切换
    private ViewPager2 viewPager2;
    // 定义一个 MediaPlayer 对象，用于播放音乐
    public MediaPlayer mediaPlayer;
    // 定义一个 boolean 变量，用于控制音效的开关
    public boolean sound = true;
    // 定义一个 SharedPreferences 对象，用于存储和读取配置信息
    private SharedPreferences sharedPreferences;

    // 定义 onCreate 方法，用于初始化 Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 调用父类的 onCreate 方法
        super.onCreate(savedInstanceState);
        // 启用全屏模式
        EdgeToEdge.enable(this);
        // 设置布局文件
        setContentView(R.layout.activity_main);
        // 初始化视图
        initView();
        // 创建一个 MediaPlayer 对象，加载背景音乐
        mediaPlayer = MediaPlayer.create(this, R.raw.bg);
        // 设置音乐循环播放
        mediaPlayer.setLooping(true);
        // 从 SharedPreferences 中读取音乐设置，如果设置为播放，则开始播放音乐
        sharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("music", true)) {
            mediaPlayer.start();
        }
        // 从 SharedPreferences 中读取音效设置，如果设置为关闭，则关闭音效
        if (!sharedPreferences.getBoolean("sound", true)) {
            sound = false;
        }
    }

    // 定义 onPause 方法，用于暂停 Activity
    @Override
    protected void onPause() {
        // 调用父类的 onPause 方法
        super.onPause();
        // 从 SharedPreferences 中读取音乐设置，如果设置为播放，则暂停音乐
        sharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("music", true)) {
            mediaPlayer.pause();
        }
    }

    // 定义 onResume 方法，用于恢复 Activity
    @Override
    protected void onResume() {
        // 调用父类的 onResume 方法
        super.onResume();
        // 从 SharedPreferences 中读取音乐设置，如果设置为播放，则开始播放音乐
        sharedPreferences = this.getSharedPreferences("config",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("music",true)) {
            mediaPlayer.start();
        }
    }

    // 定义一个名为 initView 的方法，用于初始化视图
    private void initView(){
        // 从布局文件中获取 BottomNavigationView 对象
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 从布局文件中获取 ViewPager2 对象
        viewPager2 = findViewById(R.id.view_pager);
        // 设置 BottomNavigationView 的选项选择监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // 根据选中的菜单项的 ID，切换 ViewPager2 显示的页面
                int itemId = menuItem.getItemId();
                if(itemId == R.id.tab1){
                    viewPager2.setCurrentItem(0);
                } else if (itemId == R.id.tab2) {
                    viewPager2.setCurrentItem(1);
                } else if (itemId == R.id.tab3) {
                    viewPager2.setCurrentItem(2);
                }
                return true;
            }
        });

        // 设置 ViewPager2 的适配器
        setupViewPager(viewPager2);

        // 注册 ViewPager2 的页面改变监听器
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 当页面被选中时，设置 BottomNavigationView 对应的菜单项为选中状态
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // 定义一个名为 setupViewPager 的方法，用于设置 ViewPager2 的适配器
    private void setupViewPager(ViewPager2 viewPager2) {
        // 创建一个 BottomAdapter 对象
        BottomAdapter adapter = new BottomAdapter(this);
        // 向适配器中添加 Fragment
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        adapter.addFragment(new Tab3Fragment());
        // 将适配器设置给 ViewPager2
        viewPager2.setAdapter(adapter);
    }
}