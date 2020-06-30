package com.bigoat.bbc.base;

import com.blankj.utilcode.util.LogUtils;
/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-28
 *     desc   : 日志工具
 * </pre>
 */
public interface ILog {

    static LogUtils.Config getConfig() {
        return LogUtils.getConfig();
    }

    // 动态调用
    default void logd(Object... msg) {
        LogUtils.dTag(getClass().getSimpleName(), msg);
    }

    default void loge(Object... msg) {
        LogUtils.eTag(getClass().getSimpleName(), msg);
    }

    default void logf(Object... msg) {
        LogUtils.file(getClass().getSimpleName(), msg);
    }

    default void logj(Object json) {
        LogUtils.json(getClass().getSimpleName(), json);
    }

    default void logx(String xml) {
        LogUtils.xml(getClass().getSimpleName(), xml);
    }

    // 静态调用
    static void d(Object... msg) {
        LogUtils.d(msg);
    }

    static void e(Object... msg) {
        LogUtils.e(msg);
    }

    static void f(Object... msg) {
        LogUtils.file(msg);
    }

    static void j(Object json) {
        LogUtils.json(json);
    }

    static void x(String xml) {
        LogUtils.xml(xml);
    }
}
