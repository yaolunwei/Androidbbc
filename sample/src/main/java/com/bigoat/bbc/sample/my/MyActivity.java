package com.bigoat.bbc.sample.my;

import androidx.databinding.ViewDataBinding;
import android.text.TextUtils;

import com.bigoat.bbc.base.BaseActivity;
import com.blankj.utilcode.util.ScreenUtils;

import cn.lecent.lib.tts.TTS;

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
public abstract class MyActivity<Binding extends ViewDataBinding, ViewModel extends MyViewModel> extends BaseActivity<Binding, ViewModel> {
    @Override
    protected void onResume() {
        super.onResume();
        // 设置Activity全屏
        ScreenUtils.setFullScreen(this);
    }

    protected void saveUser(String name, String password) {
        // TODO 保存用户
    }

    protected void removeUser(String name) {
        // TODO 移除用户
    }

    /**
     * 文本转语音
     *
     * @param content 内容
     */
    protected void speak(String content) {
        if (!TextUtils.isEmpty(content)) {
            TTS.speak(content);
        }
    }
}
