package com.kc.module_home.rv.titleStopAtTop;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dovar.router_annotation.Route;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.common_service.helper.recycler_view.TitleItemDecoration;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityTitleStopAtTopBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
@Route(path = "rv/titleStopAtTop")
public class TitleStopAtTopActivity extends AppBaseActivity<ActivityTitleStopAtTopBinding, BaseViewModel> {


    @Override
    protected int getLayout() {
        return R.layout.activity_title_stop_at_top;
    }

    @Override
    protected void init() {
        final List<Entity> data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (i<3){
                data.add(new Entity(0, "0组", "0组小弟"+i));
            }else if (i<6){
                data.add(new Entity(1, "1组", "1组小弟"+i));
            }else if (i<9){
                data.add(new Entity(3, "3组", "3组小弟"+i));
            }else{
                data.add(new Entity(2, "2组", "2组小弟"+i));
            }
        }
        BaseQuickAdapter<Entity, BaseViewHolder> adapter = new BaseQuickAdapter<Entity, BaseViewHolder>(R.layout.simple_item_rv, data) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, Entity item) {
                helper.setText(R.id.tv_simple_item_rv, item.name);
            }
        };
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.recyclerView.addItemDecoration(new TitleItemDecoration(this, new TitleItemDecoration.TitleDecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return data.get(position).groupId;
            }

            @Override
            public String getGroupName(int position) {
                return data.get(position).groupName;
            }
        }));
        mDataBinding.recyclerView.setAdapter(adapter);
    }
}
