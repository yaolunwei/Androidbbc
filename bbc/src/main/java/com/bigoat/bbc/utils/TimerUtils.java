package com.bigoat.bbc.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2019-08-02
 *     desc   : Timer定时器
 * </pre>
 */
public class TimerUtils {
    public static void cancel(Timer timer, TimerTask task) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
