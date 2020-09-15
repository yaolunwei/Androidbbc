package com.bigoat.bbc.adapter;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.databinding.BindingAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-07-23
 *     desc   : 背景适配器
 * </pre>
 */
public class BackgroundAdapter {

    @BindingAdapter(value = {
            "bgColor", // 背景颜色
            "bgTransparent", // 透明度
            "bgRound", // 圆形
            "borderSize", // 边界大小
            "borderColor", // 边界颜色
            "bgCornersRadius", // 圆角大小
            "bgCornersTopLeftRadius", // 圆角左上角大小
            "bgCornersTopRightRadius", // 圆角右上角大小
            "bgCornersBottomRightRadius", // 圆角左下角大小
            "bgCornersBottomLeftRadius" // 圆角右下角大小
    }, requireAll = false)
    public static void load(@NotNull View view,
                            int color,
                            int transparent,
                            boolean round,
                            int borderSize,
                            int borderColor,
                            int cornersRadius,
                            int cornersTopLeftRadius,
                            int cornersTopRightRadius,
                            int cornersBottomRightRadius,
                            int cornersBottomLeftRadius) {

        //TODO 目前只实现圆角, 增加边框颜色
        Drawable drawable = view.getBackground();
        GradientDrawable background = new GradientDrawable();

        if (drawable == null) {

        } else if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            background.setColor(colorDrawable.getColor());
        } else if (drawable instanceof GradientDrawable) {
            background = (GradientDrawable) drawable;
        } else {

        }

        if (round) {
            background.setShape(GradientDrawable.OVAL);
        } else if (cornersRadius == 0) {
            float[] floats = new float[]{
                    cornersTopLeftRadius,cornersTopLeftRadius,
                    cornersTopRightRadius,cornersTopRightRadius,
                    cornersBottomRightRadius,cornersBottomRightRadius,
                    cornersBottomLeftRadius,cornersBottomLeftRadius};
            background.setCornerRadii(floats);
        } else {
            background.setCornerRadius(cornersRadius);
        }

        view.setBackground(background);
    }
}
