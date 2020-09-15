package com.bigoat.bbc.base;

import android.app.Application;
import android.view.Gravity;


import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 参考 @{link https://www.jianshu.com/p/f665366b2a47}
 * 参考 @{link https://developer.android.com/reference/android/app/Application}
 *
 * 所有的应用的 {@link Application} 都应该继承 @{link BaseApplication}
 *
 * 应用场景:
 *
 * 1. 初始化 应用程序级别 的资源，如全局对象、环境配置变量等
 * 2. 数据共享、数据缓存，如设置全局共享变量、方法等
 * 3. 获取应用程序当前的内存使用情况，及时释放资源，从而避免被系统杀死
 * 4. 监听 应用程序 配置信息的改变，如屏幕旋转等
 * 5. 监听应用程序内 所有Activity的生命周期
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication sInstance;
    private final Map<String, Object> mData = new LinkedHashMap<>();

    public static BaseApplication getInstance() {
        return sInstance;
    }

    /**
     * Application 实例创建时调用
     *
     * 请不要执行耗时操作，否则会拖慢应用程序启动速度
     */
    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        // 初始化工具
        Utils.init(this);

        // 初始化网络
        OkGo.getInstance().init(this);

        // 初始化配置
        ILog.getConfig().setStackOffset(2);
        IToast.getConfig().setGravity(Gravity.BOTTOM, 0, 30);

        myCreate();

        boolean firstRun = SPUtils.getInstance().getBoolean("firstRun", true);
        if (firstRun) {
            SPUtils.getInstance().put("firstRun", false);
            myFirstRun();
        }

    }

    /**
     * 执行初始化操作
     */
    public abstract void myCreate();

    /**
     * 首次运行
     */
    public void myFirstRun() {}

    /**
     * 存储全局数据
     *
     * @param key key
     * @param value value
     */
    public void putData(String key, Object value) {
        if (key == null) {
            return;
        }

        if (value == null) {
            mData.remove(key);
        } else {
            mData.put(key, value);
        }
    }

    /**
     * 获取全局数据
     *
     * @param key key
     * @return 数据
     */
    public Object getData(String key) {
        return mData.get(key);
    }
}
