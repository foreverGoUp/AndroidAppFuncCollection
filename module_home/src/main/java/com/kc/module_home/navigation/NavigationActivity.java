package com.kc.module_home.navigation;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.functions.Consumer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dovar.router_annotation.Route;
import com.dovar.router_api.router.DRouter;
import com.kc.common_service.base.BaseActivity;
import com.kc.common_service.base.vm.BaseViewModel;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityNavigationBinding;
import com.king.zxing.CaptureActivity;
import com.king.zxing.Intents;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

@Route(path = "home/navigation")
public class NavigationActivity extends BaseActivity<ActivityNavigationBinding, BaseViewModel> {

    @Override
    protected int getLayout() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void init() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<MySection> data = new ArrayList<>();
        data.add(new MySection(true, "首页"));
        data.add(new MySection("新闻娱乐类多标签"));
        data.add(new MySection(true, "原料风格"));
        data.add(new MySection("可折叠标题栏"));
        data.add(new MySection("toolbar+DrawerLayout+NavigationView+浮动按钮+可交互吐司+卡片式布局+下拉刷新+CoordinatorLayout+AppBarLayout"));
        data.add(new MySection(true, "RecyclerView"));
        data.add(new MySection("refresh+侧滑选项"));
        data.add(new MySection("mvvmAdapter"));
        data.add(new MySection("titleStopAtTop"));
        data.add(new MySection(true, "其他"));
        data.add(new MySection("二维码扫描"));
        data.add(new MySection("对话框"));

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
}
