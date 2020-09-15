package com.bigoat.bbc.sample.baselivedata;

import androidx.lifecycle.Transformations;

import com.bigoat.bbc.base.BaseLiveData;
import com.bigoat.bbc.sample.my.MyViewModel;

public class BaseLiveDataViewModel extends MyViewModel {
    public BaseLiveData<String> nullData = new BaseLiveData<>();
    public BaseLiveData<Integer> integerData = new BaseLiveData<>(123);
    public BaseLiveData<Boolean> booleanData = new BaseLiveData<>(true);
    public BaseLiveData<String> helloData = new BaseLiveData<>("hello base live data.");
    public BaseLiveData<Phone> phoneData = new BaseLiveData<>(new Phone("一加8 Pro", 3500));

    @Override
    protected void onCreate() {
        helloData.observeForever(s -> logd("BaseLiveDataViewModel: ", s));
    }
}
