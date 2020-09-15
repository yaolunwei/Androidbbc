package com.bigoat.bbc.sample.autoArg;

import android.graphics.Color;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-06-10
 *     desc   : 金毛狗
 * </pre>
 */
public class GoldenRetrieverGog extends Dog {
    private Color color;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoldenRetrieverGog{" +
                "color=" + color +
                ", name='" + name + '\'' +
                ", sex='" + getSex() + '\'' +
                ", tag='" + getTag() + '\'' +
                '}';
    }
}
