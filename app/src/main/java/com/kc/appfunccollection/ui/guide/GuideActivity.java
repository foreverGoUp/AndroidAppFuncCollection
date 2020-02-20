package com.kc.appfunccollection.ui.guide;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dovar.router_annotation.Route;
import com.dovar.router_api.router.DRouter;
import com.kc.appfunccollection.R;
import com.kc.appfunccollection.databinding.ActivityGuideBinding;
import com.kc.common_service.base.AppBaseActivity;

import java.util.ArrayList;
import java.util.List;

import ckc.android.develophelp.lib.adapter.ViewPagerAdapter;
import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;

@Route(path = "app/guide")
public class GuideActivity extends AppBaseActivity<ActivityGuideBinding, BaseViewModel> {

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        initVp();
    }

    private void initVp() {
        int pageNum = 3;
        List<View> data = new ArrayList<>(pageNum);
        int[] imageRes = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        for (int i = 0; i < pageNum; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imageRes[i]);
            if (i==pageNum-1){
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DRouter.navigator("home/navigation").navigateTo(GuideActivity.this);
                        finish();
                    }
                });

            }

            data.add(imageView);
        }
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(data);
        mDataBinding.vp.setAdapter(mAdapter);
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
