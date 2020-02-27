package com.kc.module_home.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearSnapHelper;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dovar.router_annotation.Route;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityVideoBrowseBinding;
import com.kc.module_home.widget.videoBrowse.VideoBrowseLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;

@Route(path = "widget/videoBrowse")
public class VideoBrowseActivity extends AppBaseActivity<ActivityVideoBrowseBinding, BaseViewModel> {

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_video_browse;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        mDataBinding.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mDataBinding.et.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    return;
                }
                int num = Integer.parseInt(input);
//                Float num = Float.parseFloat(input);

//                helper.scrollToPosition(num);
                mDataBinding.rv.smoothScrollToPosition(num);
//                mDataBinding.rv1.setFlingMaxVelocity(num);
            }
        });


        //提供调用smoothScrollToPosition方法跳转到指定位置时最后item在屏幕中心
        VideoBrowseLinearLayoutManager linearLayoutManager = new VideoBrowseLinearLayoutManager(this);
        //若使用该LinearLayoutManager和LinearSnapHelper搭配使用，会造成最后有一丝明显卡顿的效果
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mDataBinding.rv.setLayoutManager(linearLayoutManager);

//        mDataBinding.rv.setFlingRatio(0.5f);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(String.valueOf(i));
        }
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_video_browse, data) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
            }
        };
        mDataBinding.rv.setAdapter(adapter);
        //提供手指滑动列表停止时item在屏幕中心
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mDataBinding.rv);
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
