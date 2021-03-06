package com.kc.module_home.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentRecommendBinding;

import java.util.ArrayList;

import ckc.android.develophelp.lib.base.mvvm.BaseFragment;
import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;


public class RecommendFragment extends BaseFragment<FragmentRecommendBinding, BaseViewModel> {

    public RecommendFragment() {
    }

    public static RecommendFragment newInstance() {
        Bundle args = new Bundle();

        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int onConfigViewLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void OnInit(View view) {
        super.OnInit(view);
        String[] titles = new String[10];
        for (int i = 0; i < 10; i++) {
            titles[i] = "标题"+i;
        }
        ArrayList<Fragment> fragments = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            fragments.add(VpFragment.newInstance(i));
        }
        mDataBinding.slidingTabLayout.setViewPager(mDataBinding.viewPager, titles, getActivity(), fragments);
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
