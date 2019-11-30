package com.kc.androiddevelophelp.base.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected DB mDataBinding;

    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, getLayout());
        init();
    }

    /**
     * 获得布局文件
     * */
    protected abstract int getLayout();
//    /**
//     * 初始化ViewModel
//     */
//    protected abstract void initViewModel();
//
//    /**
//     * 绑定ViewModel
//     */
//    protected abstract void bindViewModel();
    /**
     * 初始化
     * */
    protected abstract void init();

    protected boolean isUseStatusBarSpace(){
        return false;
    }
}
