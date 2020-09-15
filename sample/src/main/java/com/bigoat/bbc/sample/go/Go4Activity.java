package com.bigoat.bbc.sample.go;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.Go2ActivityBinding;
import com.bigoat.bbc.sample.databinding.Go4ActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.my.MyViewModel;

public class Go4Activity extends MyActivity<Go4ActivityBinding, MyViewModel> {

    @Override
    protected int myView() {
        return R.layout.go_4_activity;
    }

    @Override
    protected void myCreate(@NonNull Go4ActivityBinding bind, @NonNull MyViewModel vm) {
        bind.name.setText(getClass().getSimpleName());

        bind.go.setOnClickListener(view -> go(Go5Activity.class));

        bind.resultGo.setOnClickListener(view -> go(Go5Activity.class, 4));


        bind.setVm(vm);
    }

    @Override
    protected void myGoResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 4 && (resultCode == 53 || resultCode == 50)) {
            finish(resultCode, data);
        } else {
            showToastInfo("返回结果：requestCode=" + requestCode + ", resultCode=" + resultCode);
        }
    }
}
