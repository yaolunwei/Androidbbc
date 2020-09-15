package com.bigoat.bbc.base;

import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 18-11-19
 *     desc   : 基础ViewModel进行逻辑处理
 * </pre>
 */
public abstract class BaseViewModel extends ViewModel implements IToast, ILog {

    private String tag;

    private boolean isCreate = false;

    protected BaseLiveData<String> progressData = new BaseLiveData<>();

    protected BaseLiveData<Integer> finishResultCodeData = new BaseLiveData<>();

    /**
     * 监听Activity 和 Fragment 生命周期
     */
    protected void create() {
        if (!isCreate) {
            tag = getClass().getSimpleName();
            onCreate();
            isCreate = true;
        }
    }

    protected void onCreate() {}

    protected void onStart() {}

    protected void onResume() {}

    protected void onPause() {}

    protected void onStop() {}

    protected void onDestroy() {}

    protected void showProgress(@NonNull String msg) {
        progressData.value(msg);
    }

    protected void showProgress() {
        progressData.value("");
    }

    protected void hideProgress() {
        progressData.value(null);
    }

    public void finish(int resultCode) {
        finishResultCodeData.setValue(resultCode);
    }

    public void finish() {
        finishResultCodeData.setValue(-1);
    }

}
