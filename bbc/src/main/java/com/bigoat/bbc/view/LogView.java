package com.bigoat.bbc.view;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bigoat.bbc.R;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2019-08-09
 *     desc   : 日志调试界面
 * </pre>
 */
public class LogView extends FrameLayout {
    // https://blog.csdn.net/rozol/article/details/86658357
    private Application application;
    private WindowManager wm = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

    private  int statusHeight;
    private float mLastX;
    private float mLastY;
    private float mStartX;
    private float mStartY;
    private long mDownTime;
    private long mUpTime;
    private int sW;
    private int sH;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private boolean isMove=false;
    private boolean isShow = false;

    private View view;
    private TextView logView;
    private TextView cleanLog;
    private ScrollView logScrollView;
    private View zoomView;

    public LogView(Application application) {
        this(application,null);
        this.application = application;
        initView(application.getBaseContext());
    }

    public LogView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public LogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        view = inflate(context, R.layout.log_layout, null);
        addView(view);
    }

    float w, h;

    private void initView(Context context) {
        view = inflate(application.getBaseContext(), R.layout.log_layout, null);
        logView = view.findViewById(R.id.log);
        logScrollView = view.findViewById(R.id.logScrollView);
        zoomView = view.findViewById(R.id.zoom);
        cleanLog = view.findViewById(R.id.cleanLog);
        cleanLog.setOnClickListener(view -> cleanLog());

        // 缩放处理
        zoomView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX();
                //statusHeight是系统状态栏的高度
                y = event.getRawY() - statusHeight;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:    //按下
//                        mTouchStartX = event.getX();
//                        mTouchStartY = event.getY();
//                        mStartX = event.getRawX();
//                        mStartY = event.getRawY();
//                        mDownTime = System.currentTimeMillis();
//                        isMove = false;
                        break;
                    case MotionEvent.ACTION_MOVE:   //手指移动
                        w = x;
                        h = y;

                        Log.d("TOUCH", "end: " + x + " - " + y);
//                        updateViewPosition();

//                        isMove = true;
                        break;
                    case MotionEvent.ACTION_UP:    //手抬起
//                        mLastX = event.getRawX();
//                        mLastY = event.getRawY();
//                        mUpTime = System.currentTimeMillis();
//                        //按下到抬起的时间大于500毫秒,并且抬手到抬手绝对值大于20像素处理点击事件
//                        if(mUpTime - mDownTime < 500){
//                            if(Math.abs(mStartX- mLastX )< 20.0 && Math.abs(mStartY - mLastY) < 20.0){
//                                new Thread(){
//                                    @Override
//                                    public void run() {
//                                    }
//                                }.start();
//                            }
//                        }

                        break;
                }
                return true;
            }
        });


        List<String> runningList = queryAllRunningAppInfo();
        Spinner packageFilter = view.findViewById(R.id.packageFilter);
        ArrayAdapter<String> packageFilterAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, runningList);
        packageFilter.setAdapter(packageFilterAdapter);

        addView(view);

//        readLog("", "", "", false);
//        showLog();

    }

    private int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");    //使用反射获取实例
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        //statusHeight是系统状态栏的高度
        y = event.getRawY() - statusHeight;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //按下
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                mDownTime = System.currentTimeMillis();
                isMove = false;
                break;
            case MotionEvent.ACTION_MOVE:   //手指移动
                updateViewPosition();
                isMove = true;
                break;
            case MotionEvent.ACTION_UP:    //手抬起
                mLastX = event.getRawX();
                mLastY = event.getRawY();
                mUpTime = System.currentTimeMillis();
                //按下到抬起的时间大于500毫秒,并且抬手到抬手绝对值大于20像素处理点击事件
                if(mUpTime - mDownTime < 500){
                    if(Math.abs(mStartX- mLastX )< 20.0 && Math.abs(mStartY - mLastY) < 20.0){
                        new Thread(){
                            @Override
                            public void run() {
                            }
                        }.start();
                    }
                }

                break;
        }
        return true;
    }
    private void updateViewPosition() {
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y- mTouchStartY);
        if (h != 0) {
            wmParams.height = (int)(h - wmParams.y);
        }
        if (w!=0) {
            wmParams.width = (int)(w - wmParams.x);
        }
        Log.d("Test", x + " - " + y);
        wm.updateViewLayout(this, wmParams);
    }

    private void createBackButton() {
        wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        wmParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            wmParams.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        wmParams.format= PixelFormat.RGBA_8888; // 设置背景图片
        wmParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ;//
//        wmParams.gravity = Gravity.LEFT| Gravity.TOP;//
        wmParams.gravity = Gravity.CENTER;//
        wmParams.width = 600;
        wmParams.height = 500;
        wmParams.x = widthPixels;   //设置位置像素
        wmParams.y = heightPixels;
        wm.addView(this, wmParams);
    }

    public void show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(application.getBaseContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + application.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);
        } else {
            createBackButton();
        }
    }

    public void hide() {
            try {
                wm.removeView(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    // 获取正在运行应用
    private List<String> queryAllRunningAppInfo() {
        List<String> list = new ArrayList<>();
        ActivityManager mActivityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
            for (int i = 0; i < runningAppProcessInfos.size(); i++) {
                ActivityManager.RunningAppProcessInfo info = runningAppProcessInfos.get(i);
                list.add(info.processName + "("+ info.pid +")");
            }

            return list;
        } else {
            String currInfo = application.getPackageName() + "("+android.os.Process.myPid( )+")";
            list.add(currInfo);
            return list;
        }
    }


    private Queue<String> logs = new LinkedList<>();
    private boolean readLogEnable = true;

    /**
     * 读取系统日志
     * @param pkg
     * @param level *:W
     * V —— Verbose（最低，输出得最多）
     * D —— Debug
     * I —— Info
     * W —— Warning
     * E —— Error
     * F —— Fatal
     * S —— Silent（最高，啥也不输出）
     * @return
     */
    private void readLog(String pkg, String level, String keyword, boolean isClean) {
        OutputStream os;
        InputStream is;
        try {
//            adb logcat | grep  `adb shell ps | grep -E "com.bigoat.bbc.sample" | awk '{print $2}'`
//            String logcat = "logcat ``";

//            Process exec = Runtime.getRuntime().exec(isRoot() ? "su" : "sh");
            Process exec = Runtime.getRuntime().exec("su");
            os = exec.getOutputStream();
            if (isClean) {
                os.write("logcat -c \n".getBytes());
            }
            os.write("logcat \n".getBytes());
            os.flush();

            is = exec.getInputStream();
            new Thread() {
                @Override
                public void run() {
                    int len = 0;
                    byte[] buf = new byte[1024];
                    logs.offer("test log");
                    while (readLogEnable) {
                        try {
                            if (-1 != (len = is.read(buf))) {
                                String log = new String(buf).trim();
//                                if (logs.size() == 100) logs.clear();
                                logs.offer(log);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLog() {
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        String log = logs.poll();
                        if (log==null || log.equals("null")) continue;
                        logView.post(new Runnable() {
                            @Override
                            public void run() {
                                String content = logView.getText().toString() + log + "\n";
                                logView.setText(content);
                                logScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });


//                        ThreadUtils.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                String content = logView.getText().toString() + log + "\n";
//                                logView.setText(content);
////                                postDelayed(new Runnable() {
////                                    @Override
////                                    public void run() {
////
////                                    }
////                                }, 10);
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    // 判断是否有Root权限
    private boolean isRoot() {
        File file = null;
        String[] paths = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/", "/su/bin/"};
        try {
            for (String path : paths) {
                file = new File(path + "su");
                if (file.exists() && file.canExecute()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void cleanLog() {
        logs.clear();
        logView.setText("");
        readLog("", "", "", true);
    }
}
