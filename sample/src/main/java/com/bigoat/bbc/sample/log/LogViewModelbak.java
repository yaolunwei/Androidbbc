package com.bigoat.bbc.sample.log;

import com.bigoat.bbc.base.BaseLiveData;
import com.bigoat.bbc.sample.my.MyViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LogViewModelbak extends MyViewModel {
    String[] running = new String[]{"logcat","-s","adb logcat *: W"};

    public BaseLiveData<String> logData = new BaseLiveData<>();

    private void addLog(String log) {
        logData.value(logData.value() + "\n" + log);
    }

    public void onCreate1() {
        logData.setValue("读取日志：\n");

        try {
            Process exec = Runtime.getRuntime().exec("sh");
            OutputStream os = exec.getOutputStream();
            os.write("logcat".getBytes());
            os.write("\n".getBytes());
            os.flush();

            InputStream is = exec.getInputStream();

            new Thread(){
                @Override
                public void run() {
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while (true) {
                        try {
                            if (-1 != (len = is.read(buf))) {
                                addLog(new String(buf).trim());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        ILog.d("Log1", TimeUtils.getNowString());
                    }
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
