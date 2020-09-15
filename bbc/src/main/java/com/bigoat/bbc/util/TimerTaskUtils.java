package com.bigoat.bbc.util;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2019-11-11
 *     desc   : 定时任务工具类
 * </pre>
 */
public class TimerTaskUtils {
    private static Timer timer = new Timer();
    private static Map<String, TimerTask> timerTasks = new HashMap();

    public static void start(String tag, long period, final TaskCallback callback) {
        start(tag, 0, period, callback);
    }

    public static void start(String tag, long delay, long period, final TaskCallback callback) {
        if (timerTasks.get(tag) != null) {
            cancel(tag);
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                callback.onTick();
            }
        };

        timer.scheduleAtFixedRate(task, delay, period);
        timerTasks.put(tag, task);

    }

    public static void cancelAll() {
        for (String tag : timerTasks.keySet()) {
            TimerTask timer = timerTasks.get(tag);
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            timerTasks.remove(tag);
        }

    }

    public static void cancel(String tag) {
        TimerTask timer = timerTasks.get(tag);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timerTasks.remove(tag);
    }

    public interface TaskCallback {
        void onTick();
    }
}
