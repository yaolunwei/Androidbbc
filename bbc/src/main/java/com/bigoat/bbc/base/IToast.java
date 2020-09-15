package com.bigoat.bbc.base;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-28
 *     desc   : 吐司接口
 * </pre>
 */
public interface IToast {
    enum TYPE {
        NORMAL(), INFO, WARN, ERROR, SUCCESS
    }
    class Config {
        static Config config = new Config();

        public Config setGravity(final int gravity) {
            ToastUtils.setGravity(gravity, 0, 0);
            return this;
        }

        public Config setGravity(final int gravity, final int xOffset, final int yOffset) {
            ToastUtils.setGravity(gravity, xOffset, yOffset);
            return this;
        }

        public Config setBgColor(@ColorInt final int backgroundColor) {
            ToastUtils.setBgColor(backgroundColor);
            return this;
        }

        public Config setBgResource(@DrawableRes final int bgResource) {
            ToastUtils.setBgResource(bgResource);
            return this;
        }

        public Config setMsgColor(@ColorInt final int msgColor) {
            ToastUtils.setMsgColor(msgColor);
            return this;
        }

        public Config setMsgTextSize(final int textSize) {
            ToastUtils.setMsgTextSize(textSize);
            return this;
        }
    }

    static Config getConfig() {
        return Config.config;
    }

    default void toast(TYPE type, String msg) {
        ToastUtils.setBgColor(Color.parseColor("#353A3E"));
        ToastUtils.setMsgColor(Color.WHITE);
        ToastUtils.showShort(msg);
    }

    // 动态调用
    default void showToast(@NonNull String msg) {
        toast(TYPE.NORMAL, msg);
    }

    @Deprecated
    default void showToastNormal(@NonNull String msg) {
        toast(TYPE.NORMAL, msg);
    }

    default void showToastInfo(@NonNull String msg) {
        toast(TYPE.INFO, msg);
    }

    default void showToastWarn(@NonNull String msg) {
        toast(TYPE.WARN, msg);
    }

    default void showToastError(@NonNull String msg) {
        toast(TYPE.ERROR, msg);
    }

    default void showToastSuccess(@NonNull String msg) {
        toast(TYPE.SUCCESS, msg);
    }

    // 静态调用
    static void show(@NonNull String msg) {
        staticToast(TYPE.NORMAL, msg);    }

    @Deprecated
    static void showNormal(@NonNull String msg) {
        staticToast(TYPE.NORMAL, msg);
    }

    static void showInfo(@NonNull String msg) {
        staticToast(TYPE.INFO, msg);
    }

    static void showWarn(@NonNull String msg) {
        staticToast(TYPE.WARN, msg);
    }

    static void showError(@NonNull String msg) {
        staticToast(TYPE.ERROR, msg);
    }

    static void showSuccess(@NonNull String msg) {
        staticToast(TYPE.SUCCESS, msg);
    }

    static void staticToast(TYPE type, String msg) {
        ToastUtils.setBgColor(Color.parseColor("#353A3E"));
        ToastUtils.setMsgColor(Color.WHITE);
        ToastUtils.showShort(msg);
    }
}
