package com.kc.appfunccollection.ui.welcome;

import com.blankj.utilcode.util.BarUtils;
import com.dovar.router_api.router.DRouter;
import com.kc.appfunccollection.R;
import com.kc.appfunccollection.databinding.ActivityWelcomeBinding;
import com.kc.common_service.base.AppBaseActivity;

public class WelcomeActivity extends AppBaseActivity<ActivityWelcomeBinding, ViewModel> implements INavigator {

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        BarUtils.setStatusBarLightMode(this, true);

        mViewModel = new ViewModel(this);
        mDataBinding.setVm(mViewModel);

        mViewModel.countdown(true);
    }

    @Override
    public void onClickJump() {
        // app/guide
        DRouter.navigator("home/navigation").navigateTo(this);
        finish();
    }
}
