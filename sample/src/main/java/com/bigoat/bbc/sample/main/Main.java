package com.bigoat.bbc.sample.main;

import com.bigoat.bbc.base.BaseActivity;

public class Main {
    public String name;
    public Class<BaseActivity> activity;

    public Main(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Main{" +
                "name='" + name + '\'' +
                '}';
    }
}
