package com.kc.androiddevelophelp.base.mvvm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.androiddevelophelp.base.common.BaseLazyFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * 建议当片段通过viewpager adapter展示时使用懒加载方式。
 * 懒加载相关方法
 * onActivityCreated、setUserVisibleHint、lazyLoad、onLazyLoad、onUserVisibleAfterLazyLoad、onResumeAfterLazyLoad
 * */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseLazyFragment {

    protected DB mDataBinding;

    protected VM mViewModel;

    protected View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, hashCode() + " onCreateView >>>>>>>>>>>>>>>>>>>>>>");
        if (mView == null){
            mView = inflater.inflate(getLayout(), container, false);
            mDataBinding = DataBindingUtil.bind(mView);
            init();
        }
        return mView;
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
}
