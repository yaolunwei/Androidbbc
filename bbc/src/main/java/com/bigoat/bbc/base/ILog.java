package com.bigoat.bbc.base;

import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-28
 *     desc   : 日志接口
 * </pre>
 */
public interface ILog {

    static LogUtils.Config getConfig() {
        return LogUtils.getConfig();
    }

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
}
