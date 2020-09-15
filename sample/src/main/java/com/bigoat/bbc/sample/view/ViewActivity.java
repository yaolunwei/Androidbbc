package com.bigoat.bbc.sample.view;

import androidx.annotation.NonNull;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.ViewActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.view.image.ImageViewActivity;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-28
 *     desc   : Toast
 * </pre>
 */
public class ViewActivity extends MyActivity<ViewActivityBinding, ViewViewModel> {

    @Override
    protected int myView() {
        return R.layout.view_activity;
    }

    @Override
    protected void myCreate(@NonNull ViewActivityBinding bind, @NonNull ViewViewModel vm) {
        setTitle("自定义View");

        bind.imageView.setOnClickListener(v -> go(ImageViewActivity.class));

        bind.setVm(vm);
    }
}
