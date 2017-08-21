package com.tdin360.zjw.marathon.weight;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 设置RecyclerView GridLayoutManager item间距
 * 目前仅适用于 RecyclerView GridLayoutManager
 * Created by admin on 17/8/7.
 * @author zhangzhijun
 */

public class ZJSpaceItem extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.color.black};
    private int space;
    private int mColumn;
    private boolean isHeader,isFooter;



    /**
     * 无头无尾时调用该构造
     * @param space 每个item的间距
     * @param column 每行显示的元素个数
     */
    public ZJSpaceItem(int space, int column){

        this.space=space;
        this.mColumn=column;
    }

    /**
     * 有头有尾或只有头或尾时调用该构造
     * @param space 每个item的间距
     * @param column  每行显示的元素个数
     * @param isHeader 是否有头
     * @param isFooter 是否有尾
     */
    public ZJSpaceItem(int space, int column, boolean isHeader, boolean isFooter){

        this.space=space;
        this.mColumn=column;
        this.isHeader=isHeader;
        this.isFooter=isFooter;


    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if((layoutManager instanceof GridLayoutManager)||(layoutManager instanceof StaggeredGridLayoutManager)) {


            /**
             * 如果有头头的位置不需要进行间距设置
             */
            if (isHeader) {

                if (parent.getChildAdapterPosition(view) == 0) return;

            }
            /**
             * 如果有尾尾部位置不需要进行间距设置
             */
            if (isFooter) {

                if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1)return;

            }

            int position = parent.getChildAdapterPosition(view);


            /**
             * 在有头的情况下进行间距设置
             */
            if(isHeader) {

                /**
                 * 设置第一行的顶部间距
                 */
                if(position<=mColumn){

                    outRect.top=space;
                }

                if (position != 0) {

                    outRect.left = space;
                    outRect.bottom = space;
                }

                if (position % mColumn == 0) {

                    outRect.right = space;
                }


                /**
                 * 在无头的情况下进行设置间距
                 */
            }else {

                /**
                 * 设置第一行的顶部间距
                 */
                if(position<mColumn){

                    outRect.top=space;
                }
                int column = position % mColumn;
                outRect.left = space - column * space / mColumn;
                outRect.right = (column + 1) * space / mColumn;
                outRect.bottom=space;
            }

        }else{ // LinearLayoutManager

            if(isHeader){


              if(parent.getChildAdapterPosition(view)==0)return;
            }
            if(isFooter){

                if(parent.getChildAdapterPosition(view)==parent.getAdapter().getItemCount()-1)return;
            }

           outRect.bottom=space;
         }

    }

}
