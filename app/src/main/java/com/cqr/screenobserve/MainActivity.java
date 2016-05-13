package com.cqr.screenobserve;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private Button mBtnOne, mBtnTwo;

    private ScreenObserve mScreenObserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mScreenObserve = new ScreenObserve(this);
        screenUpdate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart()");
        screenUpdate();
    }

    /**
     * 如果使用 onStop方法,锁屏会直接走onStop()方法
     */
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop()");
//        mScreenObserve.stopScreenStateUpdate();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        screenUpdate();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause()");
//        mScreenObserve.stopScreenStateUpdate();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        mScreenObserve.stopScreenStateUpdate();
    }

    /**
     * 监听屏幕是否锁屏
     */
    private void screenUpdate() {

        mScreenObserve.requestScreenStateUpdate(new ScreenObserve.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.d(TAG, "screen on");
            }

            @Override
            public void onScreenOff() {
                Log.d(TAG, "screen off");
            }
        });
    }

    /**
     * 模拟同一个类中,启动广播,会重复启动,不会覆盖和报错
     */
    private void initView() {
        mBtnOne = (Button) findViewById(R.id.btn_one);
        mBtnTwo = (Button) findViewById(R.id.btn_two);

        mBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SeconedActivity.class);
                startActivity(intent);
            }
        });


        mBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"seconed");
//                screenUpdate();
            }
        });

    }
}
