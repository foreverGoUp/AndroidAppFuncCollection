package com.kc.module_home.material;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dovar.router_annotation.Route;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.common_service.helper.recycler_view.DividerVerticalGridItemDecoration;
import com.kc.common_service.util.RxUtils;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityToolbarBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

@Route(path = "home/toolbar")
public class ToolbarActivity extends AppBaseActivity<ActivityToolbarBinding, BaseViewModel> {

    @Override
    protected boolean onConfigStatusBarTransparent() {
        return true;
    }

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_toolbar;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        //toolbar
        setSupportActionBar(mDataBinding.toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setTitle("标题");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
        //导航view
        mDataBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDataBinding.drawLayout.closeDrawers();
                return true;
            }
        });
        //浮动按钮
        mDataBinding.floatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar
                Snackbar.make(v, "已经删除历史记录", Snackbar.LENGTH_LONG)
                        .setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                ToastUtils.showShort("已取消");
                            }
                        }).show();
            }
        });
        //卡片式布局
        List<AEntity> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(new AEntity(R.drawable.news_head, "运动员"));
        }
        final BaseQuickAdapter<AEntity, BaseViewHolder> adapter = new BaseQuickAdapter<AEntity, BaseViewHolder>(R.layout.item_card_view, data) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, AEntity item) {
                Glide.with(ToolbarActivity.this)
                        .load(item.getId())
                        .into((ImageView) helper.getView(R.id.iv_item));

                helper.setText(R.id.tv_item_title, item.getName());
            }
        };
        mDataBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mDataBinding.recyclerView.addItemDecoration(new DividerVerticalGridItemDecoration(this, com.kc.common_service.R.drawable.shape_grid_divider_transparent, 3));
        mDataBinding.recyclerView.setAdapter(adapter);

        //下拉刷新
        mDataBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(2, TimeUnit.SECONDS)
                        .compose(RxUtils.<Long>applySchedulers())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                List<AEntity> data = new ArrayList<>();
                                Random random = new Random(50);
                                for (int i = 0; i < 20; i++) {
                                    data.add(new AEntity(R.drawable.news_head, "灌篮高手"+random.nextInt(108)));
                                }
                                adapter.setNewData(data);
                                mDataBinding.swipeRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.recommend){
//            ToastUtils.showShort("click recommend");
        }else if (item.getItemId() == R.id.focus){
//            ToastUtils.showShort("click focus");
        }else if (item.getItemId() == R.id.find){
//            ToastUtils.showShort("click find");
        }else if (item.getItemId() == android.R.id.home){
            mDataBinding.drawLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
