package com.kc.module_home.navigation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dovar.router_annotation.Route;
import com.dovar.router_api.router.DRouter;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityNavigationBinding;
import com.kc.module_home.pagerAdapter.PagerAdapterTestActivity;
import com.king.zxing.Intents;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;
import io.reactivex.functions.Consumer;

@Route(path = "home/navigation")
public class NavigationActivity extends AppBaseActivity<ActivityNavigationBinding, BaseViewModel> {

    private final String P1 = "新闻滑动多标题(仿斗鱼首页)";
    private final String P2 = "可折叠标题栏（图片沉浸状态栏）";
    private final String P3 = "各种原料控件（toolbar+DrawerLayout+NavigationView+浮动按钮+可交互吐司+卡片式布局+下拉刷新+CoordinatorLayout+AppBarLayout）";
    private final String P4 = "下拉刷新和侧滑删除";
    private final String P5 = "mvvm列表";
    private final String P6 = "列表标题悬停";
    private final String P7 = "二维码扫描";
    private final String P8 = "对话框";
    private final String P9 = "多片段页面";
    private final String P30 = "小自定义控件合集";
    private final String P31 = "视频浏览";

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<MySection> data = new ArrayList<>();
        data.add(new MySection(true, "首页"));
        data.add(new MySection(P1, "home/tab"));
        data.add(new MySection(true, "原料风格"));
        data.add(new MySection(P2, "home/news_detail"));
        data.add(new MySection(P3, "home/toolbar"));
        data.add(new MySection(true, "RecyclerView"));
        data.add(new MySection(P4, "rv/refresh"));
        data.add(new MySection(P5, "rv/mvvmAdapter"));
        data.add(new MySection(P6, "rv/titleStopAtTop"));
        data.add(new MySection(true, "其他"));
        data.add(new MySection(P7, null));
        data.add(new MySection(P8, "other/dialog"));
        data.add(new MySection(P9, null));
        data.add(new MySection(true, "自定义控件"));
        data.add(new MySection(P30, "widget/small"));
        data.add(new MySection(P31, "widget/videoBrowse"));

        SectionAdapter adapter = new SectionAdapter(R.layout.item_navigation, R.layout.head_item_navigation, data);
        mDataBinding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySection section = (MySection) adapter.getData().get(position);
                switch (section.header) {
                    case P7:
                        new RxPermissions(NavigationActivity.this).request(Manifest.permission.CAMERA)
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean grant) throws Exception {
                                        if (grant) {
                                            //直接使用库自带的扫描Activity，但只有扫描功能
//                                            startActivityForResult(new Intent(NavigationActivity.this, CaptureActivity.class),10);
                                            DRouter.navigator("other/qrCode").navigateTo(NavigationActivity.this);
                                        } else {
//                                            ToastUtils.showShort("抱歉，需要启用相机权限才能使用");
                                        }
                                    }
                                });
                        break;
                    case P9:
                        startActivity(new Intent(NavigationActivity.this, PagerAdapterTestActivity.class));
                        break;
                    default:
                        if (section.t != null) {
                            DRouter.navigator(section.t).navigateTo(NavigationActivity.this);
                        }
                        break;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra(Intents.Scan.RESULT);
//            ToastUtils.showShort(result);
        }
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
