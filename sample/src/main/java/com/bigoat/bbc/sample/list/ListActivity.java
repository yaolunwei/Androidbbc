package com.bigoat.bbc.sample.list;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.ListActivityBinding;
import com.bigoat.bbc.sample.databinding.ListItemBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.blankj.utilcode.util.PermissionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class ListActivity extends MyActivity<ListActivityBinding, ListViewModel> {

    @Override
    protected int myView() {
        return R.layout.list_activity;
    }

    @Override
    protected void myCreate(@NonNull ListActivityBinding bind, @NonNull ListViewModel vm) {
        BaseQuickAdapter<Item, BaseViewHolder> adapter = new BaseQuickAdapter<Item, BaseViewHolder>(R.layout.list_item) {
            @Override
            protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
                // 绑定 view
                DataBindingUtil.bind(viewHolder.itemView);
            }

            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, Item item) {
                if (item == null) {
                    return;
                }

                // 获取 Binding
                ListItemBinding binding = baseViewHolder.getBinding();
                if (binding != null) {
                    // 设置数据
                    binding.setItem(item);
                    binding.executePendingBindings();
                }
            }
        };

        bind.list.setLayoutManager(new LinearLayoutManager(this));
        bind.list.setAdapter(adapter);

        vm.itemData.observe(this, items -> adapter.setNewInstance(items));

        bind.setVm(vm);
    }

}
