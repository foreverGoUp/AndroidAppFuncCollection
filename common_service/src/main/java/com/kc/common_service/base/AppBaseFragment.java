package com.kc.common_service.base;

import com.kc.androiddevelophelp.base.mvvm.BaseFragment;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;

import androidx.databinding.ViewDataBinding;

public abstract class AppBaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<DB, VM> {
}
