package com.kc.module_home.other;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dovar.router_annotation.Route;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.androiddevelophelp.dialog.BaseDialog;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityDialogBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

@Route(path = "other/dialog")
public class DialogActivity extends AppBaseActivity<ActivityDialogBinding, BaseViewModel> {

    @Override
    protected int getLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void init() {
        List<String> data = new ArrayList<>();
        data.add("提示");
        data.add("确认");
        data.add("修改名称");
        data.add("底部");
        data.add("列表");
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.simple_item_rv, data) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_simple_item_rv, item);
            }
        };
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerView.setAdapter(adapter);

        AlertDialog alertDialog;
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
//                        new NormalDialog(DialogActivity.this)
//                                .title("提示对话框")
//                                .content("你太帅了")
//                                .btnNum(1)
//                                .btnText("确定")
//                                .show();
                        new BaseDialog<BaseDialog>(DialogActivity.this)
                                .setLayoutId(R.layout.dialog_tip)
                                .setText(R.id.tv_content, "山外有山人外有人")
                                .setOnClickListener(R.id.bt_ok, new BaseDialog.OnClickListener() {
                                    @Override
                                    public void onClick(BaseDialog dialog) {
                                        dialog.dismiss();
                                    }
                                })
                                .setBackgroundDimAmount(0.5f)
                                .setAnimationStyleRes(R.style.AnimationDialog)
                                .show();
                        break;
                }
            }
        });
    }
}
