package com.kc.module_home.home;

import android.os.Bundle;

import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseFragment;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentFindBinding;


public class FindFragment extends AppBaseFragment<FragmentFindBinding, BaseViewModel> {

    public FindFragment() {
        DEBUG = true;
    }

    public static FindFragment newInstance() {
        Bundle args = new Bundle();

        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGetViewLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initViewModelAndBindDataBinding() {

    }
}
