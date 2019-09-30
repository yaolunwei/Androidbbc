package com.bigoat.bbc.sample;

import android.support.annotation.NonNull;
import android.view.View;

import com.bigoat.bbc.sample.databinding.MainActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.utils.PermissionConstants;
import com.bigoat.bbc.utils.PermissionUtils;

import java.util.List;

public class MainActivity extends MyActivity<MainActivityBinding, MainViewMode> {

    @Override
    protected int myView() {
        return R.layout.main_activity;
    }

    @Override
    protected void myCreate(@NonNull MainActivityBinding bind, @NonNull MainViewMode vm) {
        bind.btn1.setOnClickListener(v -> {
            PermissionUtils.permission(PermissionConstants.CAMERA).callback(new PermissionUtils.FullCallback() {
                @Override
                public void onGranted(List<String> permissionsGranted) {
                    logj(permissionsGranted);
                }

                @Override
                public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                    logj(permissionsDeniedForever);
                    logj(permissionsDenied);
                }
            }).request();

            PermissionUtils.permission(PermissionConstants.LOCATION).callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    logj("onGranted");
                }

                @Override
                public void onDenied() {
                    logj("onDenied");

                }
            }).request();
//            showProgress("5秒后自动隐藏");
//            v.postDelayed(() -> hideProgress(), 5000);
        });

        bind.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionUtils.launchAppDetailsSettings();
            }
        });


    }

}
