package com.kc.module_home.rv;

import android.content.Context;
import android.view.View;
import android.view.animation.Interpolator;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dovar.router_annotation.Route;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.common_service.util.RandomUtils;
import com.kc.common_service.util.RxUtils;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityRecyclerViewBinding;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

@Route(path = "rv/refresh")
public class RecyclerViewActivity extends AppBaseActivity<ActivityRecyclerViewBinding, BaseViewModel> {

    BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    //是否测试纸飞机效果的下拉刷新，如果是，则需要修改界面的布局文件引用和Bindindg类。
    //ActivityRecyclerViewFlyRefreshBinding
    //R.layout.activity_recycler_view_fly_refresh
    private boolean testFlyRefresh = false;
    private List<String> styleTitles = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void init() {
        //rv refresh styles
        styleTitles.add("ClassicsHeader");
        styleTitles.add("BezierRadarHeader");//com.scwang.smartrefresh.layout.header.
        styleTitles.add("BezierCircleHeader");
        styleTitles.add("DeliveryHeader");
        styleTitles.add("DropBoxHeader");
        styleTitles.add("FunGameBattleCityHeader");
        styleTitles.add("FunGameHitBlockHeader");
        styleTitles.add("MaterialHeader");
        styleTitles.add("PhoenixHeader");
        styleTitles.add("StoreHouseHeader");
        styleTitles.add("TaurusHeader");
        styleTitles.add("WaterDropHeader");
        styleTitles.add("WaveSwipeHeader");//com.scwang.smartrefresh.header.
        styleTitles.add("ClassicsFooter");
        styleTitles.add("BallPulseFooter");//com.scwang.smartrefresh.layout.footer.
        mDataBinding.rvRefreshStyles.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mDataBinding.rvRefreshStyles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.simple_item_hor_rv, styleTitles) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_simple_item_rv, item);
            }
        };
        mDataBinding.rvRefreshStyles.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    clickStyle(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //recyclerView
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("item "+i);
        }
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_swipe_menu, data) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_simple_item_rv, item);
            }
        };
        mDataBinding.recyclerView.setAdapter(mAdapter);


        //smartRefreshLayout
        if (testFlyRefresh){
            initFlyRefresh();
        }else {

        }
        mDataBinding.smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Observable.timer(3, TimeUnit.SECONDS)
                        .compose(RxUtils.<Long>applySchedulers())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (testFlyRefresh){
                                    //FlyRefreshHeader。通知刷新完成，这里改为通知Header，让纸飞机飞回来
//                                    mDataBinding.flyRefreshHeader.finishRefresh(new AnimatorListenerAdapter() {
//                                        public void onAnimationEnd(Animator animation) {
//                                            //在纸飞机回到原位之后添加数据效果更真实
//                                            refreshListData();
//                                        }
//                                    });
                                }else {
                                    //其他Header
                                    int size = refreshListData();
                                    if (size == 20){
                                        mDataBinding.smartRefreshLayout.finishRefresh();
                                    }else {
                                        mDataBinding.smartRefreshLayout.finishRefreshWithNoMoreData();
                                    }
                                }
                            }
                        });
            }
        });
        mDataBinding.smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Observable.timer(2, TimeUnit.SECONDS)
                        .compose(RxUtils.<Long>applySchedulers())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                List<String> data = new ArrayList<>();
                                int size = RandomUtils.nextInt(19, 20);
                                int start = RandomUtils.nextInt(50);
                                for (int i = start; i < start+size; i++) {
                                    data.add("new item "+i);
                                }
                                mAdapter.addData(data);
                                ToastUtils.showShort("新增数据条数："+size);
                                if (size == 20){
                                    mDataBinding.smartRefreshLayout.finishLoadMore();
                                }else {
                                    mDataBinding.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            }
                        });
            }
        });
    }

    private void clickStyle(int position) throws Exception{
        if (position<2){
            String prefix = "com.scwang.smartrefresh.layout.header.";
            RefreshHeader refreshHeader = (RefreshHeader)Class.forName(prefix+styleTitles.get(position)).getConstructor(Context.class).newInstance(this);
            mDataBinding.smartRefreshLayout.setRefreshHeader(refreshHeader);
        }else if (position<13){
            String prefix = "com.scwang.smartrefresh.header.";
            RefreshHeader refreshHeader = (RefreshHeader)Class.forName(prefix+styleTitles.get(position)).getConstructor(Context.class).newInstance(this);
            mDataBinding.smartRefreshLayout.setRefreshHeader(refreshHeader);
        }else {
            String prefix = "com.scwang.smartrefresh.layout.footer.";
            RefreshFooter refreshFooter = (RefreshFooter)Class.forName(prefix+styleTitles.get(position)).getConstructor(Context.class).newInstance(this);
            mDataBinding.smartRefreshLayout.setRefreshFooter(refreshFooter);
        }
    }

    private int refreshListData(){
        List<String> data = new ArrayList<>();
        int size = RandomUtils.nextInt(18, 20);
        int start = RandomUtils.nextInt(50);
        for (int i = start; i < start+size; i++) {
            data.add("item "+i);
        }
        mAdapter.setNewData(data);
        ToastUtils.showShort("刷新数据条数："+size);

        return size;
    }

    private void initFlyRefresh() {
//        //header绑定山峰场景view和纸飞机view
//        mDataBinding.flyRefreshHeader.setUp(mDataBinding.mountainSceneView, mDataBinding.flyView);
//        mDataBinding.smartRefreshLayout.setReboundInterpolator(new ElasticOutInterpolator());//设置回弹插值器，会带有弹簧震动效果
//        mDataBinding.smartRefreshLayout.setReboundDuration(800);//设置回弹动画时长
//        //设置 让 AppBarLayout 和 RefreshLayout 的滚动同步 并不保持 toolbar 位置不变
//        mDataBinding.smartRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
//            @Override
//            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//                mDataBinding.appBarLayout.setTranslationY(offset);
//                mDataBinding.toolbar.setTranslationY(-offset);
//            }
//        });
    }

    public static class ElasticOutInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float t) {
            if (t == 0) return 0;
            if (t >= 1) return 1;
            float p=.3f;
            float s=p/4;
            return ((float)Math.pow(2,-10*t) * (float)Math.sin( (t-s)*(2*(float)Math.PI)/p) + 1);
        }
    }
}
