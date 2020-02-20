package com.kc.module_home.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.dovar.router_annotation.Route;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityTabBinding;

import ckc.android.develophelp.lib.base.common.RootFragment;
import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;

@Route(path = "home/tab")
public class TabActivity extends AppBaseActivity<ActivityTabBinding, BaseViewModel> {

    private int mCurrentPos = -1;
    private String[] mFragmentTags = new String[]{RecommendFragment.class.getSimpleName(), FocusFragment.class.getSimpleName(), FindFragment.class.getSimpleName()};

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_tab;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        RootFragment.DEBUG = true;

        initTabLayout();
        switchFragment(0);
    }

    @Override
    protected boolean onConfigStatusBarTransparent() {
        return true;
    }

    private void initTabLayout() {
        mDataBinding.tabLayout.getTabSelectedIndicator().setAlpha(0);
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setCustomView(R.layout.home_tab_recommend), true);
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setCustomView(R.layout.home_tab_focus));
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setCustomView(R.layout.home_tab_find));
        mDataBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().setSelected(true);
                switchFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 获取某个标签位置的片段
     */
    private Fragment getFragment(int pos){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mFragmentTags[pos]);
        if (fragment == null){
            switch (pos){
                case 0:
                    fragment = RecommendFragment.newInstance();
                    break;
                case 1:
                    fragment = FocusFragment.newInstance();
                    break;
                case 2:
                    fragment = FindFragment.newInstance();
                    break;
            }
        }

        return fragment;
    }

    /**
     * 显示片段
     */
    private void switchFragment(int pos){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentPos != -1){
            Fragment hideFragment = getFragment(mCurrentPos);
            fragmentTransaction.hide(hideFragment);
//            hideFragment.setUserVisibleHint(false);//模拟片段交给vp adapter显示以致可以使用片段的懒加载方法
        }

        mCurrentPos = pos;
        Fragment showFragment = getFragment(pos);
//        showFragment.setUserVisibleHint(true);//模拟片段交给vp adapter显示以致可以使用片段的懒加载方法
        if (!showFragment.isAdded()) {
            fragmentTransaction.add(R.id.frameLayout_frag_container, showFragment, mFragmentTags[pos]);
        }
        fragmentTransaction.show(showFragment).commitAllowingStateLoss();
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
