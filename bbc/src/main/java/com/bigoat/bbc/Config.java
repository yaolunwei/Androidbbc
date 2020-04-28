package com.bigoat.bbc;

import com.bigoat.bbc.base.IToast;


public class Config {
    public static Config sInstance = new Config();


    public static Config getInstance() {
        return sInstance;
    }

    public Config() {
    }

}
