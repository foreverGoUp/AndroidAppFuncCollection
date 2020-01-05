package com.kc.androiddevelophelp.base.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 根活动
 * 2019-12-01 ckc
 */
public abstract class RootActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();
    //是否调试
    public static boolean DEBUG = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.e(TAG, "[DEBUG] onCreate");
        //如果不是子类设置内容视图，则交给我来设置
        if (isSubClassSetContentView()) {
            onSubClassSetContentView();
        } else {
            setContentView(onGetContentViewLayout());
        }
        init(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (DEBUG) Log.e(TAG, "[DEBUG] onRestoreInstanceState(Bundle)");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if (DEBUG) Log.e(TAG, "[DEBUG] onRestoreInstanceState(Bundle,PersistableBundle)");

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DEBUG) Log.e(TAG, "[DEBUG] onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEBUG) Log.e(TAG, "[DEBUG] onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (DEBUG) Log.e(TAG, "[DEBUG] onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (DEBUG) Log.e(TAG, "[DEBUG] onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (DEBUG) Log.e(TAG, "[DEBUG] onSaveInstanceState(Bundle,PersistableBundle)");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (DEBUG) Log.e(TAG, "[DEBUG] onSaveInstanceState(Bundle)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.e(TAG, "[DEBUG] onDestroy");
    }

    /**
     * 是否让子类设置内容视图
     */
    protected abstract boolean isSubClassSetContentView();

    /**
     * 子类设置内容视图
     */
    protected abstract void onSubClassSetContentView();

    /**
     * 获得界面内容布局，由子类实现
     */
    protected abstract int onGetContentViewLayout();

    /**
     * 界面初始化
     */
    protected abstract void init(Bundle savedInstanceState);

}
