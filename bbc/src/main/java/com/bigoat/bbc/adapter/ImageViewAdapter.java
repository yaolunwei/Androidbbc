package com.bigoat.bbc.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bigoat.bbc.R;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageViewAdapter {

    @SuppressLint("CheckResult")
    @BindingAdapter(value = {
            "imageSrc", // 源图片
            "imageHolder", // 占位图
            "imageError", // 错误图
            "imageRound", // 圆形图片
            "imageGray", // 图片黑白
            "imageBlur", // 图片模糊
            "imageCornersRadius", // 圆角大小
            "imageCornersTopLeftRadius", // 圆角左上角大小
            "imageCornersTopRightRadius", // 圆角右上角大小
            "imageCornersBottomRightRadius", // 圆角左下角大小
            "imageCornersBottomLeftRadius" // 圆角右下角大小
    }, requireAll = false)
    public static void load(@NotNull ImageView view,
                            Object src,
                            Drawable holder,
                            Drawable error,
                            boolean round,
                            boolean gray,
                            int blur,
                            int cornersRadius,
                            int cornersTopLeftRadius,
                            int cornersTopRightRadius,
                            int cornersBottomRightRadius,
                            int cornersBottomLeftRadius) {


        RequestOptions options = new RequestOptions();
        if (holder != null) options.placeholder(holder);
        if (error != null) options.error(error);

        MultiTransformation<Bitmap> transformation = new MultiTransformation<Bitmap>(new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL));

        if (round) {
            transformation = new MultiTransformation<Bitmap>(transformation, new CropCircleTransformation());
        }

        if (gray) {
            transformation = new MultiTransformation<Bitmap>(transformation, new GrayscaleTransformation());
        }

        if (blur != 0) {
            transformation = new MultiTransformation<Bitmap>(transformation, new BlurTransformation(blur));
        }

        if (!round) {
            if (cornersRadius != 0) {
                transformation = new MultiTransformation<Bitmap>(transformation, new RoundedCornersTransformation(cornersRadius, 0, RoundedCornersTransformation.CornerType.ALL));
            } else {
                if (cornersTopLeftRadius != 0) {
                    transformation = new MultiTransformation<Bitmap>(transformation, new RoundedCornersTransformation(cornersTopLeftRadius, 0, RoundedCornersTransformation.CornerType.TOP_LEFT));
                }

                if (cornersTopRightRadius != 0) {
                    transformation = new MultiTransformation<Bitmap>(transformation, new RoundedCornersTransformation(cornersTopRightRadius, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT));

                }
                if (cornersBottomRightRadius != 0) {
                    transformation = new MultiTransformation<Bitmap>(transformation, new RoundedCornersTransformation(cornersBottomRightRadius, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT));

                }
                if (cornersBottomLeftRadius != 0) {
                    transformation = new MultiTransformation<Bitmap>(transformation, new RoundedCornersTransformation(cornersBottomLeftRadius, 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT));
                }

            }
        }

        options.transform(transformation);

        load(view, src, options);
    }

    public static void load(@NotNull ImageView view, Object src, Drawable holder, Drawable error) {
        load(view, src, holder, error, false, false, 0, 0, 0, 0, 0, 0);
    }

    public static void loadCornersRadius(@NotNull ImageView view, Object src, Drawable holder, Drawable error, int cornersRadius) {
        load(view, src, holder, error, false, false, 0, cornersRadius, 0, 0, 0, 0);
    }

    public static void loadCornersRadius(@NotNull ImageView view, Object src, Drawable holder, Drawable error, int topLeftRadius, int topRightRadius, int bottomRightRadius, int bottomLeftRadius) {
        load(view, src, holder, error, false, false, 0, 0, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
    }

    public static void loadBlur(@NotNull ImageView view, Object src, Drawable holder, Drawable error, int imageBlur) {
        load(view, src, holder, error, false, false, imageBlur, 0, 0, 0, 0, 0);
    }

    public static void loadRound(@NotNull ImageView view, Object src, Drawable holder, Drawable error) {
        load(view, src, holder, error, true, false, 0, 0, 0, 0, 0, 0);
    }

    private static void load(ImageView view, Object src, RequestOptions options) {
        if (src instanceof Bitmap) {
            Bitmap bitmap = (Bitmap) src;
            Glide.with(view).load(bitmap).apply(options).into(view);
        } else if (src instanceof Drawable) {
            Drawable drawable = (Drawable) src;
            Glide.with(view).load(drawable).apply(options).into(view);
        } else if (src instanceof String) {
            String string = (String) src;
            Glide.with(view).load(string).apply(options).into(view);
        } else if (src instanceof Uri) {
            Uri uri = (Uri) src;
            Glide.with(view).load(uri).apply(options).into(view);
        } else if (src instanceof File) {
            File file = (File) src;
            Glide.with(view).load(file).apply(options).into(view);
        } else if (src instanceof URL) {
            URL url = (URL) src;
            Glide.with(view).load(url).apply(options).into(view);
        } else if (src instanceof Integer) {
            Integer integer = (Integer) src;
            Glide.with(view).load(integer).apply(options).into(view);
        } else {
            // ILog.d("加载图片失败 !");
            Glide.with(view).load(src).apply(options).into(view);
        }
    }

    // Glide
    private void loadTest(ImageView view, String url) {
        Glide.with(view)
                .load(url)
                // 请求中显示，url空、未设置error和fallback会继续显示
                .placeholder(new ColorDrawable(Color.BLACK))
                // 请求失败永久显示
                .error(new ColorDrawable(Color.RED))
                // 请求url为空时显示
                .fallback(R.drawable.brvah_sample_footer_loading)
                // 多重变换
                .transform(new FitCenter(), new CircleCrop())
                // 过度选项，BitmapTransitionOptions、DrawableTransitionOptions
                .transition(DrawableTransitionOptions.withCrossFade())
                .transition(GenericTransitionOptions.with(R.anim.fragment_close_enter))
                // 磁盘缓存策略
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                // 仅从缓存加载图片
                .onlyRetrieveFromCache(true)
                // 跳过缓存
                .skipMemoryCache(true)
                /*
                 * 设置加载尺寸
                 * 1. 如果 View 的布局参数尺寸 > 0 且 > padding，则使用该布局参数；
                 * 2. 如果 View 尺寸 > 0 且 > padding，使用该实际尺寸；
                 * 3. 如果 View 布局参数为 wrap_content 且至少已发生一次 layout ，则打印一行警告日志，建议使用 Target.SIZE_ORIGINAL 或通过 override() 指定其他固定尺寸，并使用屏幕尺寸为该请求尺寸；
                 * 4. 其他情况下（布局参数为 match_parent， 0， 或 wrap_content 且没有发生过 layout ），则等待布局完成，然后回溯到步骤1。
                 */
                .override(100, 100)
                // 目标
                .into(view);
    }
}
