package com.bigoat.bbc.sample.autoArg;

public class Animal {
    private String type;
    private String tag;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "type='" + type + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
