package com.bigoat.bbc.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 18-11-19
 *     desc   : 基础ViewModel进行逻辑处理
 * </pre>
 */
@Deprecated
public class BaseContextViewModel extends AndroidViewModel {
    private String tag;

    private boolean isCreate = false;

    protected BaseLiveData<String> progressData = new BaseLiveData<>();

    public BaseContextViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 监听Activity 和 Fragment 生命周期
     */
    public void create() {
        if (!isCreate) {
            tag = getClass().getSimpleName();
            onCreate();
            isCreate = true;
        }
    }

    public void onCreate() {}

    public void onStart() {}

    public void onResume() {}

    public void onPause() {}

    public void onStop() {}

    public void onDestroy() {}

    protected void logd(String msg) {
        LogUtils.dTag(tag, msg);
    }

    protected void loge(String msg) {
        LogUtils.eTag(tag, msg);
    }

    protected void logj(String json) {
        LogUtils.json(tag, json);
    }

    protected void logj(Object json) {
        LogUtils.json(tag, GsonUtils.toJson(json));
    }

    protected void showProgress(@NonNull String msg) {
        progressData.value(msg);
    }

    protected void showProgress() {
        progressData.value("");
    }

    protected void hideProgress() {
        progressData.value(null);
    }
}
