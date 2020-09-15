package com.bigoat.bbc.sample.go;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigoat.bbc.base.AutoArg;
import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.Go2ActivityBinding;
import com.bigoat.bbc.sample.databinding.GoActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.my.MyViewModel;
import com.blankj.utilcode.util.ActivityUtils;

public class Go2Activity extends MyActivity<Go2ActivityBinding, GoViewModel> {

    @AutoArg
    private String param;

    @Override
    protected int myView() {
        return R.layout.go_2_activity;
    }

    @Override
    protected void myCreate(@NonNull Go2ActivityBinding bind, @NonNull GoViewModel vm) {
        bind.name.setText(getClass().getSimpleName());

        showToastInfo("收到来自Activity的参数：" + param);

        bind.go.setOnClickListener(view -> go(Go3Activity.class));

        bind.resultGo.setOnClickListener(view -> go(Go3Activity.class, 2));

        bind.setVm(vm);
    }

    @Override
    protected void myGoResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2 && (resultCode == 53 || resultCode == 50)) {
            finish(resultCode, data);
        } else {
            showToastInfo("返回结果：requestCode=" + requestCode + ", resultCode=" + resultCode);
        }
    }
}
