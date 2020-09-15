package com.bigoat.bbc.sample.go;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.Go2ActivityBinding;
import com.bigoat.bbc.sample.databinding.Go3ActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.my.MyViewModel;

public class Go3Activity extends MyActivity<Go3ActivityBinding, GoViewModel> {

    @Override
    protected int myView() {
        return R.layout.go_3_activity;
    }

    @Override
    protected void myCreate(@NonNull Go3ActivityBinding bind, @NonNull GoViewModel vm) {
        bind.name.setText(getClass().getSimpleName());

        bind.go.setOnClickListener(view -> go(Go4Activity.class));

        bind.resultGo.setOnClickListener(view -> go(Go4Activity.class, 3));

        bind.setVm(vm);
    }

    @Override
    protected void myGoResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 3 && resultCode == 53) {
            showToastInfo("返回结果：" + resultCode + ", data: " + data.getStringExtra("data"));
        } else if (requestCode == 3 && resultCode == 50) {
            finish(resultCode, data);
        } else {
            showToastInfo("返回结果：requestCode=" + requestCode + ", resultCode=" + resultCode);
        }

    }
}
