package com.kc.module_home.material;

import com.dovar.router_annotation.Route;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityNewsDetailBinding;

@Route(path = "home/news_detail")
public class NewsDetailActivity extends AppBaseActivity<ActivityNewsDetailBinding, BaseViewModel> {

    @Override
    protected boolean isUseStatusBarSpace() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void init() {
        setSupportActionBar(mDataBinding.toolbar);
        mDataBinding.collapsingToolbarLayout.setTitle("我是一条有个性的新闻");
        mDataBinding.tvNewsContent.setText("有个性的新闻有个性的新闻有个性的新闻有个性的新闻" +
                "有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻" +
                "有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻" +
                "有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻" +
                "有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻");
    }
}
