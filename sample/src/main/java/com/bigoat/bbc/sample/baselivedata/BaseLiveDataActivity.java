package com.bigoat.bbc.sample.baselivedata;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.BaseLiveDataActivityBinding;
import com.bigoat.bbc.sample.databinding.GoActivityBinding;
import com.bigoat.bbc.sample.go.Go2Activity;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.my.MyViewModel;

public class BaseLiveDataActivity extends MyActivity<BaseLiveDataActivityBinding, BaseLiveDataViewModel> {

    @Override
    protected int myView() {
        return R.layout.base_live_data_activity;
    }

    @Override
    protected void myCreate(@NonNull BaseLiveDataActivityBinding bind, @NonNull BaseLiveDataViewModel vm) {
        setTitle("BaseLiveData");

        bind.name.setText(getClass().getSimpleName());

        vm.helloData.observe(this, s -> logd("BaseLiveDataActivity: ", s));

        bind.setVm(vm);
    }

    @Override
    protected void myGoResult(int requestCode, int resultCode, @Nullable Intent data) {

    }

}
