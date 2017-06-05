package com.tdin360.zjw.marathon.weight;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置边距
 * Created by admin on 17/5/22.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int lineCount=0;

    /**
     *
     * @param space 边距
     * @param lineCount 每行显示的个数
     */
    public SpaceItemDecoration(int space,int lineCount) {
        this.space = space;
        this.lineCount=lineCount==0?1:lineCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildLayoutPosition(view)!=0){
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;

            //如果是最后一个元素就设置头边距
        if (parent.getChildLayoutPosition(view) %lineCount==0) {
            outRect.right = space;
        }
        }else {

            outRect.bottom=space;
        }



    }

}