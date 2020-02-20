package com.kc.common_service.base;

import android.databinding.ViewDataBinding;

import ckc.android.develophelp.lib.base.mvvm.BaseFragment;
import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;


public abstract class AppBaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<DB, VM> {
}
