package com.cqr.screenobserve;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by kitty on 16/5/13.
 */
public class SeconedActivity extends Activity {

    public static final String TAG = "SeconedActivity";

    private ScreenBroadcastReceiver mScreenReceiver;

    private ScreenStateListener mScreenStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScreenReceiver = new ScreenBroadcastReceiver();


        requestScreenStateUpdate(new ScreenStateListener() {
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
     * 第一次请求screen状态
     */
    private void firstGetScreenState() {
        if (mScreenStateListener != null) {
            mScreenStateListener.onScreenOn();
        } else {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOff();
            }
        }
    }


    /**
     * 请求screen状态更新
     *
     * @param listener
     */
    public void requestScreenStateUpdate(ScreenStateListener listener) {
        mScreenStateListener = listener;
        startScreenBroadcastReceiver();

        firstGetScreenState();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startScreenBroadcastReceiver();

    }

    /**
     * 监听屏幕是否锁屏
     */
    public class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mScreenStateListener.onScreenOn();

            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mScreenStateListener.onScreenOff();

            }
        }
    }


    /**
     * 停止screen状态更新
     */
    public void stopScreenStateUpdate() {
        unregisterReceiver(mScreenReceiver);
    }

    /**
     * 注册screen状态广播接收器
     */
    private void startScreenBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopScreenStateUpdate();
    }


    public interface ScreenStateListener {
        void onScreenOn();

        void onScreenOff();
    }

}
