package com.bigoat.bbc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bigoat.bbc.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-07-01
 *     desc   : 当前时间
 * </pre>
 */
public class CurrentTimeView extends AppCompatTextView {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 aa E HH:mm:ss", Locale.CHINESE);
    private long updateInterval = 1;
    private Runnable runnable = new Runnable() {
        Date d = new Date();
        @Override
        public void run() {
            d.setTime(System.currentTimeMillis());
            String str = sdf.format(d);
            setText(str);
            updateDateTime();
        }
    };

    /**
     * 在java代码里new的时候会用到
     */
    public CurrentTimeView(Context context) {
        this(context, null);
    }

    /**
     * 在xml布局文件中使用时自动调用
     */
    public CurrentTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    /**
     * 不会自动调用，如果有默认style时，在第二个构造函数中调用
     */
    public CurrentTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CurrentTimeView);
        String format = ta.getString(R.styleable.CurrentTimeView_ctvFormat);
        if (format!=null && !"".equals(format)) {
            sdf = new SimpleDateFormat(format, Locale.CHINESE);
        }

        long updateInterval = ta.getInteger(R.styleable.CurrentTimeView_ctvUpdateInterval, -1);
        if (updateInterval != -1) {
            this.updateInterval = updateInterval;
        }

        ta.recycle();

        setText(sdf.format(new Date()));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isEnabled()) {
            updateDateTime();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(runnable);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            updateDateTime();
        } else {
            removeCallbacks(runnable);
        }
    }

    private void updateDateTime() {
        postDelayed(runnable, updateInterval*1000);
    }
}
