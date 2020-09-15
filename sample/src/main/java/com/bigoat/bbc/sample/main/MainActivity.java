package com.bigoat.bbc.sample.main;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bigoat.bbc.base.BaseDataBindingAdapter;
import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.baselivedata.BaseLiveDataActivity;
import com.bigoat.bbc.sample.databinding.MainActivityBinding;
import com.bigoat.bbc.sample.databinding.MainItemLayoutBinding;
import com.bigoat.bbc.sample.autoArg.JumpActivity;
import com.bigoat.bbc.sample.my.MyActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-29
 *     desc   : 案例首页
 * </pre>
 */
public class MainActivity extends MyActivity<MainActivityBinding, MainViewMode> {

    @Override
    protected int myView() {
        return R.layout.main_activity;
    }

    @Override
    protected void myCreate(@NonNull MainActivityBinding bind, @NonNull MainViewMode vm) {


        BaseDataBindingAdapter<Main> adapter = new BaseDataBindingAdapter<Main>(R.layout.main_item_layout) {
            @Override
            protected void convert(@NotNull BaseViewHolder holder, Main main) {
                if (main==null) return;
                MainItemLayoutBinding binding = getBinding(holder.itemView);
                if (binding != null) {
                    binding.setItem(main);
                    binding.executePendingBindings();
                }
            }
        };

        bind.list.setAdapter(adapter);
        bind.list.setLayoutManager(new GridLayoutManager(this, 3));
        vm.mainData.observe(this, mains -> adapter.setNewInstance(mains));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int position) {
                go(adapter.getItem(position).activity);
            }
        });

//        go(BaseLiveDataActivity.class);
    }

}
