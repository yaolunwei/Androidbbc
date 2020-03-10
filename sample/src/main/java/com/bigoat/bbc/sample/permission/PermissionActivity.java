package com.bigoat.bbc.sample.permission;

import android.app.Activity;
import androidx.annotation.NonNull;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.PermissionActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.my.MyViewModel;
import com.blankj.utilcode.util.PermissionUtils;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2019-09-16
 *     desc   : 权限设置
 * </pre>
 */
public class PermissionActivity extends MyActivity<PermissionActivityBinding, MyViewModel> {

    @Override
    protected int myView() {
        return R.layout.permission_activity;
    }

    @Override
    protected void myCreate(@NonNull PermissionActivityBinding bind, @NonNull MyViewModel vm) {
        PermissionUtils.permission().theme(new PermissionUtils.ThemeCallback() {
            @Override
            public void onActivityCreate(Activity activity) {

            }
        });
    }

}
