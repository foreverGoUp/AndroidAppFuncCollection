package com.kc.common_service.base;

import android.graphics.Color;
import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.kc.androiddevelophelp.base.mvvm.BaseActivity;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

public abstract class AppBaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<DB, VM> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isUseStatusBarSpace()) BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
    }
}
