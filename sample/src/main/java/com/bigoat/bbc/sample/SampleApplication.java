package com.bigoat.bbc.sample;

import com.bigoat.bbc.base.BaseApplication;

import cn.lecent.lib.tts.TTS;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2019-09-16
 *     desc   : 应用Application
 * </pre>
 */
public class SampleApplication extends BaseApplication {

    @Override
    public void myCreate() {
        // 创建语音引擎
        TTS.onCreate(this);
    }

    @Override
    public void myFirstRun() {
        // 第一次运行
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // 内存低时，释放语音引擎
        TTS.onDestroy();
    }
}
