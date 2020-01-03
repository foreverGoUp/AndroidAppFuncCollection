package com.kc.common_service.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kc.androiddevelophelp.base.mvvm.BaseActivity;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;

public abstract class AppBaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<DB, VM> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (isUseStatusBarSpace()) BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
    }

    protected boolean isUseStatusBarSpace() {
        return false;
    }
}
