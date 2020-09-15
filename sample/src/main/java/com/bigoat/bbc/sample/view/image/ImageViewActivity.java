package com.bigoat.bbc.sample.view.image;

import androidx.annotation.NonNull;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.ImageviewActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.view.ViewViewModel;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-28
 *     desc   : Toast
 * </pre>
 */
public class ImageViewActivity extends MyActivity<ImageviewActivityBinding, ViewViewModel> {

    @Override
    protected int myView() {
        return R.layout.imageview_activity;
    }

    @Override
    protected void myCreate(@NonNull ImageviewActivityBinding bind, @NonNull ViewViewModel vm) {
        setTitle("图片加载变换");

        bind.setVm(vm);
    }
}
