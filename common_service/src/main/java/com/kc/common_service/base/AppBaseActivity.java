package com.kc.common_service.base;

import android.databinding.ViewDataBinding;

import ckc.android.develophelp.lib.base.mvvm.BaseActivity;
import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;


public abstract class AppBaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<DB, VM> {


    @Override
    protected boolean onConfigRootViewFitsSystemWindows() {
        return false;
    }

    @Override
    protected boolean onConfigStatusBarTransparent() {
        return false;
    }

    @Override
    protected boolean onConfigStatusBarDarkTheme() {
        return false;
    }
}
