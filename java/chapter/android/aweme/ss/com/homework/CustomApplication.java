package chapter.android.aweme.ss.com.homework;


import android.app.Application;

import java.io.InputStream;
import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.model.PullParser;

public class CustomApplication extends Application {
    private static final String VALUE = "";
    private String history;          //存储log历史记录
    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量

    }
    public void setValue(String value)
    {
        this.history = value;
    }
    public String getValue()
    {
        return history;
    }
}
