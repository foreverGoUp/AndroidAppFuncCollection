package com.kc.module_home.other;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dovar.router_annotation.Route;
import com.kc.module_home.R;

import ckc.android.develophelp.lib.base.common.RootActivity;


@Route(path = "other/dialog")
public class DialogActivity extends RootActivity {

    @Override
    protected boolean onConfigSubActivitySetContentView() {
        return false;
    }

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    protected boolean onConfigRootViewFitsSystemWindows() {
        return false;
    }

    @Override
    protected boolean onConfigStatusBarTransparent() {
        return false;
    }

    @Override
    protected boolean onConfigStatusBarDarkTheme() {
        return false;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        List<String> data = new ArrayList<>();
//        data.add("提示");
//        data.add("确认");
//        data.add("修改名称");
//        data.add("底部");
//        data.add("列表");
//        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.simple_item_rv, data) {
//            @Override
//            protected void convert(@NonNull BaseViewHolder helper, String item) {
//                helper.setText(R.id.tv_simple_item_rv, item);
//            }
//        };
//        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        mDataBinding.recyclerView.setAdapter(adapter);
//
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (position){
//                    case 0:
//                        new BaseDialog()
//                                .setLayoutId(DialogActivity.this, R.layout.dialog_tip)
//                                .setText(R.id.tv_content, "山外有山人外有人")
//                                .setOnClickListener(R.id.bt_ok, new BaseDialog.OnClickListener() {
//                                    @Override
//                                    public void onClick(BaseDialog dialog) {
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .setBackgroundDimAmount(0.5f)
//                                .setAnimationStyleRes(R.style.AnimationDialog)
//                                .show(DialogActivity.this);
//                        break;
//                }
//            }
//        });
    }

}
