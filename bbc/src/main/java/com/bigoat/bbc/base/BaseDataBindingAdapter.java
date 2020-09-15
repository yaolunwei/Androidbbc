package com.bigoat.bbc.base;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-29
 *     desc   : BaseDataBindingAdapter
 * </pre>
 */
@Deprecated
public abstract class BaseDataBindingAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public BaseDataBindingAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    protected <K extends ViewDataBinding> K getBinding(View view) {
        return DataBindingUtil.getBinding(view);
    }

}
