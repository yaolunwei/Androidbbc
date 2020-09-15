package com.bigoat.bbc.sample.go;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.Go5ActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;

public class Go5Activity extends MyActivity<Go5ActivityBinding, GoViewModel> {

    @Override
    protected int myView() {
        return R.layout.go_5_activity;
    }

    @Override
    protected void myCreate(@NonNull Go5ActivityBinding bind, @NonNull GoViewModel vm) {
        bind.name.setText(getClass().getSimpleName());

        bind.go3.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("data", bind.go3.getText());
            finish(53, intent);
        });

        bind.go.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("data", bind.go.getText());
            finish(50, intent);
        });

        bind.setVm(vm);
    }
}
