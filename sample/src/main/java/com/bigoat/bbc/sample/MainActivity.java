package com.bigoat.bbc.sample;

import android.support.annotation.NonNull;

import com.bigoat.bbc.base.BaseActivity;
import com.bigoat.bbc.sample.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity<MainActivityBinding, MainViewMode> {

    @Override
    protected int myView() {
        return R.layout.main_activity;
    }

    @Override
    protected void myCreate(@NonNull MainActivityBinding bind, @NonNull MainViewMode vm) {
        bind.btn1.setOnClickListener(v -> {
            showProgress("5秒后自动隐藏");
            v.postDelayed(() -> hideProgress(), 5000);
        });
    }

}
