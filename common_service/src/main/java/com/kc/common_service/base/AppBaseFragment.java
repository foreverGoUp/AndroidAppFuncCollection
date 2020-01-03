package com.kc.common_service.base;

import android.databinding.ViewDataBinding;

import com.kc.androiddevelophelp.base.mvvm.BaseFragment;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;


public abstract class AppBaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<DB, VM> {
}
