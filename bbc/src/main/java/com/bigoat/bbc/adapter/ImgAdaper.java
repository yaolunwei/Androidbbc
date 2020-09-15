package com.bigoat.bbc.adapter;

import androidx.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * 图片适配器
 */
@Deprecated
public class ImgAdaper {
    private static String baseUrl = "";

    public static void setBaseUrl(String baseUrl) {
        ImgAdaper.baseUrl = baseUrl;
    }

    private static String getUrl(String url) {
        if (url!=null && !url.startsWith("http")) {
            return baseUrl + url;
        } else {
            return url;
        }
    }

    /**
     *
     * 图片地址
     * app:imgUrl
     * app:imgDrawable
     * app:imgResId
     * app:imgBitmap
     *
     * 图片占位图
     * app:imgHolderResId
     * app:imgHolderDrawable
     *
     * 图片错误
     * app:imgErrorResId
     * app:imgErrorDrawable
     *
     *
     * 
     * 图片圆形
     *
     *
     * 图片圆角
     *
     *
     */

    @BindingAdapter(value = {"img", "imgHolder", "imgError", "imgCorner"}, requireAll = false)
    public static void loadImage(ImageView imageView, String imgUrl, int imgHolder, int imgError, int imgCorner) {
        if (imgUrl == null) {
            return;
        }

        if (imgUrl.trim().isEmpty()) {
            imageView.setImageResource(imgError);
            return;
        }

        imgUrl = getUrl(imgUrl);

        RequestOptions options = new RequestOptions();

        if (imgHolder != 0) {
            options = options.placeholder(imgHolder);
        }

        if (imgError != 0) {
            options = options.error(imgError);
        }

        if (imgCorner != 0) {
            options = options.transform(new RoundedCorners(imgCorner));
        }

        Glide.with(imageView)
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }


    @BindingAdapter(value = {"img", "imgHolder", "imgError", "imgRoundedCorner"}, requireAll = false)
    public static void loadImage(ImageView imageView, String imgUrl, Drawable imgHolder, Drawable imgError, int imgCorner) {
        if (imgUrl == null) {
            return;
        }

        if (imgUrl.trim().isEmpty()) {
            imageView.setImageDrawable(imgError);
            return;
        }

        imgUrl = getUrl(imgUrl);

        RequestOptions options = new RequestOptions();

        if (imgHolder != null) {
            options = options.placeholder(imgHolder);
        }

        if (imgError != null) {
            options = options.error(imgError);
        }

        if (imgCorner != 0) {
            options = options.transform(new RoundedCorners(imgCorner));
        }

        Glide.with(imageView)
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

}