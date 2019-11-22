package com.kc.androiddevelophelp.base.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity{

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置界面布局文件
        setContentView(getLayout());
        init();
    }

    //获得界面内容布局，由子类实现
    protected abstract int getLayout();
    //界面初始化
    protected abstract void init();

}
