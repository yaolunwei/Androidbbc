package com.bigoat.bbc.sample.toast;

import androidx.annotation.NonNull;

import com.bigoat.bbc.sample.R;
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
        setTitle("消息提示Toast");

        bind.setVm(vm);
    }
}
