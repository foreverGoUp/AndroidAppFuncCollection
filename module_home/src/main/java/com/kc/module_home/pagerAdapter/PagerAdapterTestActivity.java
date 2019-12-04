package com.kc.module_home.pagerAdapter;

import android.os.Bundle;

import com.kc.androiddevelophelp.adapter.BaseFragmentPagerAdapter;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityPagerAdapterTestBinding;
import com.kc.module_home.home.VpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试片段生命周期页面
 */
public class PagerAdapterTestActivity extends AppBaseActivity<ActivityPagerAdapterTestBinding, BaseViewModel> {

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_pager_adapter_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<VpFragment> fragments = new ArrayList<>();
        String[] titles = new String[6];
        for (int i = 0; i < 6; i++) {
            fragments.add(VpFragment.newInstance(i));
            titles[i] = String.valueOf(i);
        }
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getApplicationContext(), getSupportFragmentManager(), fragments);
        mDataBinding.viewPager.setAdapter(adapter);
        mDataBinding.viewPager.setCurrentItem(0);
    }

    @Override
    protected void initViewModelAndBindDataBinding() {

    }
}
