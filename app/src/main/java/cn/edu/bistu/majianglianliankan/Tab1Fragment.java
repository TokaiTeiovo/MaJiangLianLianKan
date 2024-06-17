package cn.edu.bistu.majianglianliankan;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 游戏页面
 */
public class Tab1Fragment extends Fragment {
    // 定义一个 GameConf 对象，用于存储游戏的配置信息
    private GameConf gameConf;
    // 定义一个 GameService 对象，用于处理游戏的逻辑
    private GameService gameService;
    // 定义一个 GameView 对象，用于显示游戏的界面
    private GameView gameView;
    // 定义一个 Button 对象，表示开始游戏的按钮
    private Button startButton;
    // 定义一个 TextView 对象，用于显示游戏的时间
    private TextView timeTextView;
    // 定义一个 AlertDialog.Builder 对象，用于创建游戏失败的对话框
    private AlertDialog.Builder lostDialog;
    // 定义一个 AlertDialog.Builder 对象，用于创建游戏成功的对话框
    private AlertDialog.Builder successDialog;
    // 定义一个 Timer 对象，用于计时
    private Timer timer;
    // 定义一个整数，表示游戏的时间
    private int gameTime;
    // 定义一个布尔值，表示游戏是否正在进行
    private boolean isPlaying = false;
    // 定义一个 Piece 对象，表示被选中的游戏块
    private Piece selectedPiece = null;
    // 定义一个 SoundPool 对象，用于播放音效
    private SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM , 8);
    // 定义一个整数，表示音效的 ID
    private int sdp;
    // 定义一个整数，表示音效的 ID
    private int wrong;
    // 定义一个 DataBaseHelper 对象，用于操作数据库
    private DataBaseHelper dataBaseHelper;
    // 定义一个 SQLiteDatabase 对象，用于执行 SQL 语句
    private SQLiteDatabase database;
    // 定义一个 EditText 对象，用于输入玩家的名字
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 使用 LayoutInflater 将 tab1 布局文件转换为 View 对象
        View view = inflater.inflate(R.layout.tab1, null);
        // 通过 findViewById 方法，根据 ID 获取布局文件中的 GameView 组件
        gameView = view.findViewById(R.id.gameView);
        // 设置 Fragment 有选项菜单
        setHasOptionsMenu(true);
        // 通过 findViewById 方法，根据 ID 获取布局文件中的 TextView 组件
        timeTextView = view.findViewById(R.id.timeText);
        // 设置 TextView 不可见
        timeTextView.setVisibility(View.INVISIBLE);
        // 通过 findViewById 方法，根据 ID 获取布局文件中的 Button 组件
        startButton = view.findViewById(R.id.startButton);
        // 加载音效
        sdp = soundPool.load(getContext(),R.raw.sdp,1);
        wrong = soundPool.load(getContext(),R.raw.wrong,1);
        // 初始化游戏
        init();
        // 返回视图
        return view;
    }

    // 重写 Fragment 类的 onCreateOptionsMenu 方法，用于创建选项菜单
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(1,1,1,"排行榜");
        menu.add(1,2,1,"打乱重排");
        SubMenu grade = menu.addSubMenu("难度");
        grade.setHeaderTitle("选择游戏难度");
        grade.add(1,11,2,"简单");
        grade.add(1,12,3,"容易");
        grade.add(1,13,4,"困难");
        grade.add(1,14,5,"地狱");
        menu.add(1,3,3,"重新开始");
        menu.add(1,4,4,"退出");
    }

    // 重写 Fragment 类的 onOptionsItemSelected 方法，用于处理选项菜单的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 1:
                Intent intent = new Intent();
                intent.setClass(getContext(), RankingActivity.class);
                startActivity(intent);
                break;
            case 2:
                gameService.shuffle();
                gameView.postInvalidate();
                break;
            case 3:
                if (isPlaying) {
                    startGame(0);
                } else {
                    Toast.makeText(getContext(), "你还没有开始游戏", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                Toast.makeText(getContext(), "退出游戏", Toast.LENGTH_SHORT).show();
                System.exit(0);
                break;
            case 11:
                gameConf.setxSize(5);
                gameConf.setySize(6);
                gameConf.setBeginImage();
                if (isPlaying) {
                    startGame(0);
                }
                break;
            case 12:
                gameConf.setxSize(6);
                gameConf.setySize(7);
                gameConf.setBeginImage();
                if (isPlaying) {
                    startGame(0);
                }
                break;
            case 13:
                gameConf.setxSize(7);
                gameConf.setySize(8);
                gameConf.setBeginImage();
                if (isPlaying) {
                    startGame(0);
                }
                break;
            case 14:
                gameConf.setxSize(8);
                gameConf.setySize(9);
                gameConf.setBeginImage();
                if (isPlaying) {
                    startGame(0);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 定义一个名为 init 的方法，用于初始化游戏
    private void init(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        gameConf = new GameConf(7, 8, screenWidth, screenHeight, GameConf.DEFAULT_TIME, getContext());
        gameService = new GameServiceImpl(this.gameConf);
        editText = new EditText(getContext());
        gameView.setGameService(gameService);
        gameView.setSelectImage(ImageUtil.getSelectImage(getContext()));
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                startGame(0);
                startButton.setVisibility(View.INVISIBLE);
                gameView.setBackgroundColor(0xFFFFFFFF);
                timeTextView.setVisibility(View.VISIBLE);
            }
        });

        this.gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    gameViewTouchDown(event);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    gameViewTouchUp(event);
                }
                return true;
            }
        });

        lostDialog = createDialog("Lost", "游戏失败！重新开始", R.drawable.lost).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame(0);
            }
        });

        successDialog = createDialog("Success", "游戏胜利！请输入你的名字", R.drawable.success).setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                if(name == null || name.equals("")){
                    Toast.makeText(getContext(), "请输入你的名字", Toast.LENGTH_SHORT).show();
                }else{
                    dataBaseHelper = new DataBaseHelper(getContext(),"ranking", null, 1);
                    database = dataBaseHelper.getWritableDatabase();
                    Time time = new Time();
                    time.setToNow();
                    int year = time.year;
                    int month = time.month+1;
                    int day = time.monthDay;
                    String date = year+"/"+month+"/"+day;
                    ContentValues cv = new ContentValues();
                    cv.put("name",name);
                    cv.put("time",String.valueOf(gameTime));
                    cv.put("date",date);
                    database.insert("users",null,cv);
                    Intent intent = new Intent();
                    intent.setAction("cn.edu.bistu.majianglianliankan.broadcast");
                    intent.putExtra("name",name);
                    intent.putExtra("time",String.valueOf(gameTime));
                    getActivity().sendBroadcast(intent);
                    startActivity(new Intent(getActivity(),RankingActivity.class));
                }
            }
        });
    }

    // 定义一个 Handler 对象，用于处理消息
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0x123:
                    timeTextView.setText("目前用时： " + gameTime);
                    gameTime++;
                    if (gameTime > 200) {
                        stopTimer();
                        isPlaying = false;
                        lostDialog.show();
                        return;
                    }
                    break;
            }
        }
    };

    /**
     * 暂停游戏
     */
    @Override
    public void onPause() {
        super.onPause();
        // 暂停游戏
        stopTimer();
    }

    /**
     * 重载游戏
     */
    @Override
    public void onResume() {
        super.onResume();
        // 如果处于游戏状态中
        if(isPlaying) {
            startGame(0);
        }
    }

    /**
     * 判断选中图标的匹配情况
     * @param event
     */
    private void gameViewTouchDown(MotionEvent event) {
        Piece[][] pieces = gameService.getPieces();
        if (pieces != null) {
            float touchX = event.getX();
            Log.i("X",String.valueOf(touchX));
            float touchY = event.getY();
            Log.i("Y",String.valueOf(touchY));
            Piece currentPiece = gameService.findPiece(touchX, touchY);
            if (currentPiece == null)
                return;
            this.gameView.setSelectedPiece(currentPiece);
            if (this.selectedPiece == null) {
                this.selectedPiece = currentPiece;
                this.gameView.postInvalidate();
                return;
            }
            // 表示之前已经选择了一个
            if (this.selectedPiece != null) {
                LinkInfo linkInfo = this.gameService.link(this.selectedPiece,
                        currentPiece);
                if (linkInfo == null) {
                    this.selectedPiece = currentPiece;
                    if(((MainActivity)getActivity()).sound){
                        soundPool.play(wrong, 0.5f, 0.5f, 0, 0, 1);
                    }
                    this.gameView.postInvalidate();
                } else {
                    handleSuccessLink(linkInfo, this.selectedPiece, currentPiece, pieces);
                }
            }
        }

    }

    private void gameViewTouchUp(MotionEvent e) {
        this.gameView.postInvalidate();
    }

    /**
     * 开始游戏
     * @param gameTime
     */
    private void startGame(int gameTime) {
        this.gameTime = gameTime;
        gameView.startGame();
        isPlaying = true;
        if(timer==null) {
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() {
                public void run() {
                    handler.sendEmptyMessage(0x123);
                }
            }, 0, 1000);
        }
        this.selectedPiece = null;
    }

    /**
     * 当匹配成功时，消除对应的图标
     * @param linkInfo      - 将绘制的折现节点
     * @param prePiece      - 第一个图标
     * @param currentPiece  - 第二个图标
     * @param pieces        - 图标二维数组
     */
    private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece, Piece currentPiece, Piece[][] pieces) {
        this.gameView.setLinkInfo(linkInfo);
        this.gameView.setSelectedPiece(null);
        this.gameView.postInvalidate();
        pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;
        pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
        this.selectedPiece = null;
        if(((MainActivity)getActivity()).sound){
            soundPool.play(sdp, 0.5f, 0.5f, 0, 0, 1);
        }
        if (!this.gameService.hasPieces()) {
            this.successDialog.show();
            stopTimer();
            //isPlaying = false;
        }
    }

    private AlertDialog.Builder createDialog(String title, String message, int imageResource) {
        return new AlertDialog.Builder(getContext()).setTitle(title)
                .setMessage(message).setIcon(imageResource);
    }

    /**
     * 停止计时
     */
    private void stopTimer() {
        // 停止定时器
        if(timer!=null) {
            this.timer.cancel();
            this.timer = null;
        }
    }
}
