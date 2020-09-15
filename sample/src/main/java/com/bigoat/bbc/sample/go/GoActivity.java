package com.bigoat.bbc.sample.go;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.GoActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.my.MyViewModel;

public class GoActivity extends MyActivity<GoActivityBinding, MyViewModel> {

    @Override
    protected int myView() {
        return R.layout.go_activity;
    }

    @Override
    protected void myCreate(@NonNull GoActivityBinding bind, @NonNull MyViewModel vm) {
        setTitle("Activity跳转");

        bind.name.setText(getClass().getSimpleName());

        bind.go.setOnClickListener(view -> startActivity(Go2Activity.class)
                .with("param", "GoActivity param.")
                .go(1));

        bind.noResultGo.setOnClickListener(view -> go(Go2Activity.class));
    }

    @Override
    protected void myGoResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 53 || resultCode == 50) {
            showToastInfo("返回结果：" + resultCode + ", data: " + data.getStringExtra("data"));
        } else {
            showToastInfo("返回结果：requestCode=" + requestCode + ", resultCode=" + resultCode);
        }     }
}
