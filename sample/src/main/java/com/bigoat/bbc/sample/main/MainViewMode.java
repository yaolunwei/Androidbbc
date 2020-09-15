package com.bigoat.bbc.sample.main;

import android.app.Activity;

import com.bigoat.bbc.base.BaseLiveData;
import com.bigoat.bbc.sample.autoArg.JumpActivity;
import com.bigoat.bbc.sample.baselivedata.BaseLiveDataActivity;
import com.bigoat.bbc.sample.dialog.DialogActivity;
import com.bigoat.bbc.sample.dialog.DialogViewModel;
import com.bigoat.bbc.sample.go.GoActivity;
import com.bigoat.bbc.sample.list.ListActivity;
import com.bigoat.bbc.sample.list.ListViewModel;
import com.bigoat.bbc.sample.log.LogActivity;
import com.bigoat.bbc.sample.log.LogViewModel;
import com.bigoat.bbc.sample.my.MyViewModel;
import com.bigoat.bbc.sample.toast.ToastActivity;
import com.bigoat.bbc.sample.view.ViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 19-6-12
 *     desc   :
 * </pre>
 */
public class MainViewMode extends MyViewModel {
    public BaseLiveData<List<Main>> mainData = new BaseLiveData<>();

    @Override
    public void onCreate() {
        List<Main> mains = new ArrayList<>();
        mains.add(new Main("日志组件", LogActivity.class));
        mains.add(new Main("消息提示(Toast)", ToastActivity.class));
        mains.add(new Main("Activity跳转", JumpActivity.class));
        mains.add(new Main("BaseLiveData应用", BaseLiveDataActivity.class));
        mains.add(new Main("SuperListView", ListActivity.class));
        mains.add(new Main("自定义View", ViewActivity.class));
        mains.add(new Main("Dialog", DialogActivity.class));
        mainData.value(mains);
    }
}
