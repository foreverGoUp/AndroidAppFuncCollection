package com.kc.androiddevelophelp.base.common;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 基本懒加载片段
 * 20191130 ckc
 *
 * 建议当片段通过viewpager adapter展示时使用懒加载方式。
 * 懒加载相关方法
 * onActivityCreated、setUserVisibleHint、lazyLoad、onLazyLoad、onUserVisibleAfterLazyLoad、onResumeWithUserVisible
 */
public abstract class LazyFragment extends RootFragment {

    private boolean mIsPreparedForLazyLoad = false;//是否为懒加载的视图准备好了
    private boolean mIsLazyLoaded = false;//是否已经懒加载
    private boolean mIsOnResumeCallBacked = false;//是否第一次onResume()被回调过

    /**
     * 经常被回调
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mIsLog)
            Log.e(TAG, hashCode() + " setUserVisibleHint >>>>>>>>>>>" + isVisibleToUser + ">>>>>>>>>>>");
        if (isVisibleToUser && mIsLazyLoaded) {
            onUserVisibleAfterLazyLoad();
        }
        lazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mIsLog)
            Log.e(TAG, hashCode() + " onCreateView >>>>>>>>>>>>>>>>>>>>>>");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 在onCreateView执行完后被回调
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mIsLog) Log.e(TAG, hashCode() + " onActivityCreated >>>>>>>>>>>>>>>>>>>>>>");
        mIsPreparedForLazyLoad = true;
        lazyLoad();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mIsLog) Log.e(TAG, hashCode() + " onViewStateRestored >>>>>>>>>>>>>>>>>>>>>>");

    }


    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && mIsPreparedForLazyLoad && !mIsLazyLoaded) {
            mIsLazyLoaded = true;
            onLazyLoad();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 懒加载回调
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 当片段通过viewpager adapter展示时。
     * <p>
     * 开始懒加载
     * 子类重写改方法执行第一次加载数据
     */
    protected void onLazyLoad() {
        if (mIsLog) Log.e(TAG, hashCode() + " onLazyLoad -------------------------");
    }

    /**
     * 当片段通过viewpager adapter展示时。
     * 懒加载之后，业务若需要片段每次可见时刷新数据则重写该方法执行启动刷新
     */
    protected void onUserVisibleAfterLazyLoad() {
        if (mIsLog)
            Log.e(TAG, hashCode() + " onUserVisibleAfterLazyLoad -------------------------");
    }

    /**
     * 当片段通过viewpager adapter展示时。
     * 懒加载之后，业务若需要片段每次活动从后台返回前台并处于可见时刷新数据则重写该方法执行启动刷新，该方法与onUserVisibleAfterLazyLoad不会被同时回调。
     */
    protected void onResumeWithUserVisible() {
        if (mIsLog) Log.e(TAG, hashCode() + " onResumeWithUserVisible -------------------------");
    }

    ///////////////////////////////////////////////////////////////////////////
    // 懒加载回调end
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 当片段通过viewpager adapter展示时。
     * 若
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mIsLog) Log.e(TAG, hashCode() + " onResume >>>>>>>>>>>>>>>>>>>>>>");
        if (mIsOnResumeCallBacked && getUserVisibleHint()) {
            onResumeWithUserVisible();
        } else {
            mIsOnResumeCallBacked = true;
        }
    }

    /**
     * 活动从前台切换到后台会回调该方法
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mIsLog) Log.e(TAG, hashCode() + " onSaveInstanceState >>>>>>>>>>>>>>>>>>>>>>");

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mIsLog) Log.e(TAG, hashCode() + " onStop >>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mIsLog) Log.e(TAG, hashCode() + " onDestroyView >>>>>>>>>>>>>>>>>>>>>>");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mIsLog) Log.e(TAG, hashCode() + " onDetach >>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsLog) Log.e(TAG, hashCode() + " onDestroy >>>>>>>>>>>>>>>>>>>>>>");

    }
}
