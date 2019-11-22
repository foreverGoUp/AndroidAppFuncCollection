package com.kc.module_home.home;

import android.os.Bundle;

import com.kc.common_service.base.BaseFragment;
import com.kc.common_service.base.vm.BaseViewModel;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentFindBinding;


public class FindFragment extends BaseFragment<FragmentFindBinding, BaseViewModel> {

    public static FindFragment newInstance() {
        Bundle args = new Bundle();

        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void init() {

    }
}
