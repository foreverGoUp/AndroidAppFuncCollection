package com.kc.module_home.home;

import android.os.Bundle;

import com.dovar.router_annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityTabBinding;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

@Route(path = "home/tab")
public class TabActivity extends AppBaseActivity<ActivityTabBinding, BaseViewModel> {

    private int mCurrentPos = -1;
    private String[] mFragmentTags = new String[]{RecommendFragment.class.getSimpleName(), FocusFragment.class.getSimpleName(), FindFragment.class.getSimpleName()};

    @Override
    protected boolean isUseStatusBarSpace() {
        return true;
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_tab;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTabLayout();
//        initFragments();
        switchFragment(0);
    }


    private void initFragments() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout_frag_container, RecommendFragment.newInstance(), mFragmentTags[0])
                .add(R.id.frameLayout_frag_container, FocusFragment.newInstance(), mFragmentTags[1])
                .add(R.id.frameLayout_frag_container, FindFragment.newInstance(), mFragmentTags[2])
                .hide(getSupportFragmentManager().findFragmentByTag(mFragmentTags[1]))
                .hide(getSupportFragmentManager().findFragmentByTag(mFragmentTags[2]))
                .show(getSupportFragmentManager().findFragmentByTag(mFragmentTags[mCurrentPos]))
                .commit();
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

    private void switchFragment(int pos){
//        getSupportFragmentManager().beginTransaction()
//                .hide(getSupportFragmentManager().findFragmentByTag(mFragmentTags[mCurrentPos]))
//                .show(getSupportFragmentManager().findFragmentByTag(mFragmentTags[pos]))
//                .commitAllowingStateLoss();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentPos != -1){
            fragmentTransaction.hide(getFragment(mCurrentPos));
        }

        mCurrentPos = pos;
        Fragment fragment = getFragment(pos);
        if (!fragment.isAdded()){
            fragmentTransaction.add(R.id.frameLayout_frag_container, fragment, mFragmentTags[pos]);
        }
        fragmentTransaction.show(fragment).commitAllowingStateLoss();
    }

    @Override
    protected void initViewModelAndBindDataBinding() {

    }
}
