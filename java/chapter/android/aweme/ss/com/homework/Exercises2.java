package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {
    private static final String TAG = "zqb";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        LinearLayout root = findViewById(R.id.rootview);
        int sum = getAllChildViewCount(root);
        TextView res = findViewById(R.id.textView2);
        res.append("view的数量为：" + sum);
        Log.d(TAG, "count: " + sum);

    }

    public int getAllChildViewCount(View view) {
        int count = 0;    //计数变量
        if(view == null){
            return 0;
        }
        //遍历树
        if(view instanceof ViewGroup){
            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                View child = ((ViewGroup) view).getChildAt(i);
                if(child instanceof  ViewGroup){
                    count += getAllChildViewCount(child);
                }
                else{
                    count++;
                }
            }
        }
        else count++;
        return count;
    }
}
