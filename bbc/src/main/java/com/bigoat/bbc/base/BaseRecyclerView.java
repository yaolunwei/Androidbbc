package com.bigoat.bbc.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigoat.bbc.R;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-29
 *     desc   : BaseRecyclerView
 * </pre>
 */
@Deprecated
public class BaseRecyclerView extends RecyclerView {

    @LayoutRes
    @Deprecated
    private int itemLayout;

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);

        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initialize(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (isInEditMode()) return;
        setWillNotDraw(false);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView, 0, 0);
        itemLayout = a.getResourceId(R.styleable.BaseRecyclerView_bbc_item_layout, -1);
    }
}