package com.kc.common_service.widget.rv;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class TypeSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int left, top, right, bottom;

    public TypeSpacesItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (left != 0) {
            outRect.left = left;
        }
        if (top != 0) {
            outRect.top = top;
        }
        if (right != 0) {
            outRect.right = right;
        }
        if (bottom != 0) {
            outRect.bottom = bottom;
        }
    }

}
