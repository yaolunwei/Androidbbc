package com.bigoat.bbc.util;

import android.os.CountDownTimer;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2019-11-11
 *     desc   : 倒计时任务工具类
 * </pre>
 */
public class CountDownTaskUtils {
    private static Map<String, CountDownTimer> countDownTimers = new HashMap();

    public static void start(String tag, long millisInFuture, final TaskCallback callback) {
        start(tag, millisInFuture, 1000, callback);
    }

    public static void start(String tag, long millisInFuture, long interval, final TaskCallback callback) {
       if (countDownTimers.get(tag) != null) {
           cancel(tag);
       }

        CountDownTimer countDownTimer = new CountDownTimer(millisInFuture, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                callback.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        };

       countDownTimer.start();
       countDownTimers.put(tag, countDownTimer);
    }

    public static void cancelAll() {
        for (String tag : countDownTimers.keySet()) {
            CountDownTimer timer = countDownTimers.get(tag);
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            countDownTimers.remove(tag);
        }

    }

    public static void cancel(String tag) {
       CountDownTimer timer = countDownTimers.get(tag);
       if (timer != null) {
           timer.cancel();
           timer = null;
       }
       countDownTimers.remove(tag);
    }

    public interface TaskCallback {
        void onTick(long millisUntilFinished);
        void onFinish();
    }
}
