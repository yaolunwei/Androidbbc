package com.bigoat.bbc.sample.list;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigoat.bbc.sample.databinding.ListItemBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;


public class RecyclerViewBinding {

    @BindingAdapter({"itemResId", "itemData"})
    public static <T> void loadList(RecyclerView view, @LayoutRes int itemResId, List<T> data) {
        BaseQuickAdapter<T, BaseViewHolder> adapter = new BaseQuickAdapter<T, BaseViewHolder>(itemResId) {
            @Override
            protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
                // 绑定 view
                DataBindingUtil.bind(viewHolder.itemView);
            }

            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, T item) {
                if (item == null) {
                    return;
                }

//                ListItemBinding listItemBinding = baseViewHolder.getBinding();
//                if (listItemBinding !=null) {
//                    listItemBinding.setItem((Item) item);
//                    listItemBinding.executePendingBindings();
//                }

                // 获取 Binding
                ListItemBinding binding = baseViewHolder.getBinding();
                if (binding != null) {
                    // 设置数据
                    Class clazz = binding.getClass();
                    Method method = null;
                    try {
                        method = clazz.getMethod("setItem", item.getClass());
                        method.invoke(binding, item);
                        binding.executePendingBindings();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> a, @NonNull View view, int position) {
                Item i = (Item) adapter.getItem(position);
                i.age++;
                a.notifyItemChanged(position);
//
//                Item item = new Item("添加了", "男", 100);
//                adapter.addData((T) item);
            }
        });


        adapter.setNewInstance(data);

//        .observe(this, items -> adapter.setNewInstance(items));
    }
}
