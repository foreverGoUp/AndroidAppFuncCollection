package com.kc.common_service.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.common_service.base.vm.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
/**
 *
 * 懒加载相关方法
 * onActivityCreated、setUserVisibleHint、lazyLoad、onLazyLoad
 * */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    protected DB mDataBinding;

    protected VM mViewModel;

    protected View mView;

    private boolean mIsPreparedForLazyLoad = false;
    private boolean mIsLazyLoaded = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    /**
     * 在onCreateView执行完后被回调
     * */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPreparedForLazyLoad = true;
        lazyLoad();
    }
    /**
     * 经常被回调
     * */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    /**
     * 懒加载
     * */
    private void lazyLoad(){
        if (getUserVisibleHint() && mIsPreparedForLazyLoad && !mIsLazyLoaded){
            mIsLazyLoaded = true;
            onLazyLoad();
        }
    }

    /**
     * 开始懒加载
     * */
    protected void onLazyLoad(){}
}
