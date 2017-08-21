package com.tdin360.zjw.marathon.weight.pullToControl;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 下拉刷新上拉加载的view
 * Created by admin on 17/8/14.
 */

public class CustomRecyclerView extends RecyclerView implements Pullable {

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canPullDown() {

        return false;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }


}
