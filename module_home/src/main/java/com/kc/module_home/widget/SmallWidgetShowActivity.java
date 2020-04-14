package com.kc.module_home.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dovar.router_annotation.Route;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivitySmallWidgetShowBinding;
import com.kc.module_home.widget.horizontalScrollScaleGallery.HorizontalScrollScaleGalleryHelper;
import com.kc.module_home.widget.timeAxis.CsmTimeAxisView;

import java.util.ArrayList;
import java.util.List;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;
import ckc.android.develophelp.lib.util.TimeUtils;
import ckc.android.develophelp.lib.util.ToastUtils;
import ckc.android.develophelp.lib.widget.SquareVerifyCodeView;

@Route(path = "widget/small")
public class SmallWidgetShowActivity extends AppBaseActivity<ActivitySmallWidgetShowBinding, BaseViewModel> {

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_small_widget_show;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        //csmTimeAxisLayout
        mDataBinding.csmTimeAxisLayout.getTimeAxisView().setListener(new CsmTimeAxisView.OnCsmTimeAxisListener() {
            @Override
            public void onTimeAxisMoveStart() {

            }

            @Override
            public void onTimeAxisMove(long timeMilli) {
                mDataBinding.tv3.setText(TimeUtils.getDefaultTime(timeMilli));
            }

            @Override
            public void onTimeAxisStop(long timeMilli) {
                mDataBinding.tv3.setText(TimeUtils.getDefaultTime(timeMilli));
            }
        });

//        mDataBinding.csmTimeAxisLayout.getTimeAxisView().startAutoForward();


        //csmColumnProgress
        mDataBinding.bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mDataBinding.et3.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    return;
                }
                int num = Integer.parseInt(input);
                if (num >= 0 && num <= 100) {
                    mDataBinding.csmColumnProgress.setProgress(num);
                }

//                mDataBinding.csmTimeAxisLayout.getTimeAxisView().enableDraggable(false);
            }
        });


        //squareVerifyCodeView
        mDataBinding.squareVerifyCodeView.setOnVerifyCodeListener(new SquareVerifyCodeView.OnVerifyCodeListener() {
            @Override
            public void onVerifyCodeInputComplete(final String code) {
                ToastUtils.showToast(SmallWidgetShowActivity.this, code);
            }
        });

        //annularEnergyView
        mDataBinding.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mDataBinding.et1.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    return;
                }
                int num = Integer.parseInt(input);
                if (num >= 0 && num <= 100) {
                    mDataBinding.annularEnergyView.setCurrentEnergyWithAnimation(num);
                }
            }
        });

        //HorizontalScrollScaleGalleryHelper
        mDataBinding.rv1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(String.valueOf(i));
        }
        final HorizontalScrollScaleGalleryHelper helper = new HorizontalScrollScaleGalleryHelper(mDataBinding.rv1, 8, 50, 30, 0.6f);
        final HorizontalScrollScaleGalleryAdapter adapter = new HorizontalScrollScaleGalleryAdapter(helper, data);
        mDataBinding.rv1.setAdapter(adapter);

        mDataBinding.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mDataBinding.et2.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    return;
                }
                int num = Integer.parseInt(input);
//                Float num = Float.parseFloat(input);

                helper.scrollToPosition(num);
//                mDataBinding.rv1.smoothScrollToPosition(num);
//                mDataBinding.rv1.setFlingMaxVelocity(num);
//                mDataBinding.rv1.setFlingRatio(num);
            }
        });

        mDataBinding.bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(SmallWidgetShowActivity.this, "当前位置：" + adapter.mHelper.getCurrentItemPos());
            }
        });

    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }

    //HorizontalScrollScaleGalleryHelper
    private class HorizontalScrollScaleGalleryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        HorizontalScrollScaleGalleryHelper mHelper;

        public HorizontalScrollScaleGalleryAdapter(HorizontalScrollScaleGalleryHelper helper, @Nullable List<String> data) {
            super(R.layout.item_horizontal_scroll_scale_gallery, data);
            mHelper = helper;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
            mHelper.onCreateViewHolder(parent, baseViewHolder.itemView, baseViewHolder.getLayoutPosition());
            return baseViewHolder;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            mHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        }


        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_content, item);
        }
    }
}
