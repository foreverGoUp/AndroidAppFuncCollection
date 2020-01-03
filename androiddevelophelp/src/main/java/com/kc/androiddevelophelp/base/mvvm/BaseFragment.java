package com.kc.androiddevelophelp.base.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.kc.androiddevelophelp.base.common.LazyFragment;



public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends LazyFragment {

    protected DB mDataBinding;

    protected VM mViewModel;

    @Override
    protected void init(View view) {
        mDataBinding = DataBindingUtil.bind(view);
    }


    /**
     * 初始化ViewModel并绑定DataBinding
     */
    protected abstract void initViewModelAndBindDataBinding();

}
