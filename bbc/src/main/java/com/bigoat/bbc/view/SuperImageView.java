package com.bigoat.bbc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.RawRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.bigoat.bbc.R;
import com.bigoat.bbc.base.BaseLiveData;
import com.bigoat.bbc.base.EmptyViewModel;
import com.bigoat.bbc.base.ILog;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.net.URL;


/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-07-02
 *     desc   : 图片
 * </pre>
 */
public class SuperImageView extends AppCompatImageView {
    private BaseLiveData<String> s = new BaseLiveData<>();

    // 多个共享
    private RequestOptions sharedOptions =
            new RequestOptions()
                    .placeholder(R.drawable.ic_check_white_24dp)
                    .fitCenter();

    public SuperImageView(Context context) {
        super(context);
    }

    public SuperImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public SuperImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    // 取消加载
    public void clear(Context context) {
        Glide.with(context).clear(this);
    }

    public static void load(ImageView view, Object src) {
        if (src instanceof Bitmap) {
            Bitmap bitmap = (Bitmap) src;
            Glide.with(view).load(bitmap).into(view);
        } else if (src instanceof Drawable) {
            Drawable drawable = (Drawable) src;
            Glide.with(view).load(drawable).into(view);
        } else if ( src instanceof String) {
            String string = (String) src;
            Glide.with(view).load(string).into(view);
        } else if ( src instanceof Uri) {
            Uri uri = (Uri) src;
            Glide.with(view).load(uri).into(view);
        } else if ( src instanceof File) {
            File file = (File) src;
            Glide.with(view).load(file).into(view);
        } else if ( src instanceof URL) {
            URL url = (URL) src;
            Glide.with(view).load(url).into(view);
        } else if ( src instanceof Integer) {
            Integer integer = (Integer) src;
            Glide.with(view).load(integer).into(view);
        } else {
            ILog.d("加载图片失败：" + src.getClass().getName());
        }
    }

    public static void load123(SuperImageView superImageView, Object res) {
//        CropTransformation cropTransformation = new CropTransformation();
//        RoundedCornersTransformation cropCircleTransformation = new RoundedCornersTransformation()
    }



}