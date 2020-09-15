package com.bigoat.bbc.sample.list;

import com.bigoat.bbc.base.BaseLiveData;
import com.bigoat.bbc.sample.my.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends MyViewModel {
    public BaseLiveData<List<Item>> itemData = new BaseLiveData<>();

    @Override
    protected void onCreate() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new Item("刘登富", "男", i+1));
        }
       itemData.value(items);
    }
}
