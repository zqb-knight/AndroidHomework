package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * 作业1：
 * Logcat在屏幕旋转的时候 #onStop() #onDestroy()会展示出来
 * 但我们的 mLifecycleDisplay 由于生命周期的原因(Tips:执行#onStop()之后，UI界面我们是看不到的)并没有展示
 * 在原有@see Exercises1 基础上如何补全它，让其跟logcat的展示一样?
 * <p>
 * Tips：思考用比Activity的生命周期要长的来存储？  （比如：application、static变量）
 *
 *
 */


public class Exercises1 extends AppCompatActivity {

    private static final String TAG = "zqb";
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";
    private static final String ON_RESTORE_INSTANCE_STATE = "onRestoreInstanceState";
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    private CustomApplication app;
    private TextView mLifecycleDisplay;
    private TextView historyLifecycle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CustomApplication)getApplication(); // 获得CustomApplication对象
        setContentView(R.layout.layout1);
        mLifecycleDisplay = findViewById(R.id.tv_loglifecycle);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
                String savedContent = (String) savedInstanceState.get(LIFECYCLE_CALLBACKS_TEXT_KEY);
                mLifecycleDisplay.setText(savedContent);
            }
        }
        //historyLifecycle = findViewById(R.id.historylog);
        //historyLifecycle.append(app.getValue() + "\n" + "\n");
        app.setValue(app.getValue() + "\n\n" + "Log:" + "\n" + ON_CREATE);
        Log.d(TAG, "Lifecycle Event: " + ON_CREATE);
        mLifecycleDisplay.append(app.getValue() + "\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logAndAppend(ON_RESTART);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logAndAppend(ON_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logAndAppend(ON_RESUME);
    }


    @Override
    protected void onPause() {
        super.onPause();
        logAndAppend(ON_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logAndAppend(ON_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logAndAppend(ON_DESTROY);
    }

    private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);
        mLifecycleDisplay.append(lifecycleEvent + "\n");
        app.setValue(app.getValue() + "\n" + lifecycleEvent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logAndAppend(ON_SAVE_INSTANCE_STATE);
        String content = mLifecycleDisplay.getText().toString();//当前已有的log 提取出来
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, content); //把内容存储起来
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logAndAppend(ON_RESTORE_INSTANCE_STATE);
    }

}
