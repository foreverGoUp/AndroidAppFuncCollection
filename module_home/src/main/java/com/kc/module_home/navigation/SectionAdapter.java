package com.kc.module_home.navigation;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kc.module_home.R;

import java.util.List;

import androidx.annotation.NonNull;

public class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<MySection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {
        helper.setText(R.id.tv_title, item.header);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MySection item) {
        helper.setText(R.id.tv_item_content, item.t);
    }
}
