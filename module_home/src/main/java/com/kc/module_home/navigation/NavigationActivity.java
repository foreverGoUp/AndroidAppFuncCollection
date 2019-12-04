package com.kc.module_home.navigation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dovar.router_annotation.Route;
import com.dovar.router_api.router.DRouter;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityNavigationBinding;
import com.kc.module_home.pagerAdapter.PagerAdapterTestActivity;
import com.king.zxing.Intents;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.functions.Consumer;

@Route(path = "home/navigation")
public class NavigationActivity extends AppBaseActivity<ActivityNavigationBinding, BaseViewModel> {

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<MySection> data = new ArrayList<>();
        data.add(new MySection(true, "首页"));
        data.add(1, new MySection("新闻娱乐类多标签"));
        data.add(new MySection(true, "原料风格"));
        data.add(3, new MySection("可折叠标题栏"));
        data.add(4, new MySection("toolbar+DrawerLayout+NavigationView+浮动按钮+可交互吐司+卡片式布局+下拉刷新+CoordinatorLayout+AppBarLayout"));
        data.add(new MySection(true, "RecyclerView"));
        data.add(6, new MySection("refresh+侧滑选项"));
        data.add(7, new MySection("mvvmAdapter"));
        data.add(8, new MySection("titleStopAtTop"));
        data.add(new MySection(true, "其他"));
        data.add(10, new MySection("二维码扫描"));
        data.add(11, new MySection("对话框"));
        data.add(12, new MySection("多片段页面"));

        SectionAdapter adapter = new SectionAdapter(R.layout.item_navigation, R.layout.head_item_navigation, data);
        mDataBinding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 1:
                        DRouter.navigator("home/tab").navigateTo(NavigationActivity.this);
                        break;
                    case 3:
                        DRouter.navigator("home/news_detail").navigateTo(NavigationActivity.this);
                        break;
                    case 4:
                        DRouter.navigator("home/toolbar").navigateTo(NavigationActivity.this);
                        break;
                    case 6:
                        DRouter.navigator("rv/refresh").navigateTo(NavigationActivity.this);
                        break;
                    case 7:
                        DRouter.navigator("rv/mvvmAdapter").navigateTo(NavigationActivity.this);
                        break;
                    case 8:
                        DRouter.navigator("rv/titleStopAtTop").navigateTo(NavigationActivity.this);
                        break;
                    case 10:
                        new RxPermissions(NavigationActivity.this).request(Manifest.permission.CAMERA)
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean grant) throws Exception {
                                        if (grant){
                                            //直接使用库自带的扫描Activity，但只有扫描功能
//                                            startActivityForResult(new Intent(NavigationActivity.this, CaptureActivity.class),10);
                                            DRouter.navigator("other/qrCode").navigateTo(NavigationActivity.this);
                                        }else {
                                            ToastUtils.showShort("抱歉，需要启用相机权限才能使用");
                                        }
                                    }
                                });
                        break;
                    case 11:
                        DRouter.navigator("other/dialog").navigateTo(NavigationActivity.this);
                        break;
                    case 12:
                        startActivity(new Intent(NavigationActivity.this, PagerAdapterTestActivity.class));
                        break;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode== Activity.RESULT_OK){
            String result = data.getStringExtra(Intents.Scan.RESULT);
            ToastUtils.showShort(result);
        }
    }

    @Override
    protected void initViewModelAndBindDataBinding() {

    }
}
