package com.bigoat.bbc.sample.my;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.ViewDataBinding;
import android.text.TextUtils;

import com.bigoat.bbc.base.BaseActivity;
import com.bigoat.bbc.base.BaseViewModel;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;

import java.util.Objects;


/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2019-09-16
 *     desc   : 所有Activity的基类，进行一些公共与应用业务有关的操作例如：
 *              1. 设置全屏
 *              2. 保存用户信息
 *              3. TTS(文本转语音)
 * </pre>
 */
public abstract class MyActivity<Binding extends ViewDataBinding, ViewModel extends BaseViewModel> extends BaseActivity<Binding, ViewModel> {
    @Override
    protected void onResume() {
        super.onResume();
        // 设置Activity全屏
        ScreenUtils.setFullScreen(this);
    }

    protected void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
