package com.kc.common_service.base;

import android.graphics.Color;
import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.kc.common_service.base.vm.BaseViewModel;

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
        if (isUseStatusBarSpace()) BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
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
