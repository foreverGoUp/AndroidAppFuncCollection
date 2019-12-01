package com.kc.androiddevelophelp.base.mvvm;

import com.kc.androiddevelophelp.base.common.RootActivity;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends RootActivity {

    protected DB mDataBinding;

    protected VM mViewModel;

    @Override
    protected boolean isSubClassSetContentView() {
        return true;
    }

    @Override
    protected void onSubClassSetContentView() {
        mDataBinding = DataBindingUtil.setContentView(this, onGetContentViewLayout());
    }

    /**
     * 初始化ViewModel并绑定DataBinding
     */
    protected abstract void initViewModelAndBindDataBinding();


}
