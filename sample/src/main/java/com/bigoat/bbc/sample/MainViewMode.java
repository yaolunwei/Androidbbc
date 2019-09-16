package com.bigoat.bbc.sample;

import com.bigoat.bbc.sample.my.MyViewModel;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 19-6-12
 *     desc   :
 * </pre>
 */
public class MainViewMode extends MyViewModel {

    @Override
    public void onCreate() {
        showProgress("MainViewMode 创建完成");
        hideProgress();
    }
}
