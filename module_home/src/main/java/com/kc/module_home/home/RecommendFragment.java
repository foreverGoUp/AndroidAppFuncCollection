package com.kc.module_home.home;

import android.os.Bundle;

import com.kc.androiddevelophelp.base.mvvm.BaseFragment;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentRecommendBinding;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class RecommendFragment extends BaseFragment<FragmentRecommendBinding, BaseViewModel> {

    public static RecommendFragment newInstance() {
        Bundle args = new Bundle();

        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void init() {
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
}
