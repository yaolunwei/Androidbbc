package com.bigoat.bbc.sample.toast;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bigoat.bbc.base.ILog;
import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.User;
import com.bigoat.bbc.sample.databinding.ToastActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-28
 *     desc   : Toast
 * </pre>
 */
public class ToastActivity extends MyActivity<ToastActivityBinding, ToastViewModel> {

    @Override
    protected int myView() {
        return R.layout.toast_activity;
    }

    @Override
    protected void myCreate(@NonNull ToastActivityBinding bind, @NonNull ToastViewModel vm) {

        logd("This is logd log.");
        loge("This is loge log.");
        logj(new User());
        logx("<a>this is logx log.</a>");

        Log.d("TAG", "android log");
        bind.setVm(vm);
    }

}
