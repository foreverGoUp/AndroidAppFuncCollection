package com.kc.androiddevelophelp.base.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 根活动
 * 2019-12-01 ckc
 */
public abstract class RootActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果不是子类设置内容视图，则交给我来设置
        if (isSubClassSetContentView()) {
            onSubClassSetContentView();
        } else {
            setContentView(onGetContentViewLayout());
        }
        init(savedInstanceState);
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
