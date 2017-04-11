package com.tdin360.zjw.marathon.weight.pullToControl;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

public class PullableListView extends ListView implements Pullable,AbsListView.OnScrollListener
{

	private  boolean isCanUp;
	public PullableListView(Context context)
	{
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.setOnScrollListener(this);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown()
	{
		if (getCount() == 0)
		{
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0)
		{
			// 滑到ListView的顶部了
			return true;
		} else
			return false;
	}

	@Override
	public boolean canPullUp()
	{
		if (getCount() == 0)
		{
			// 没有item的时候不可以上拉加载
			return false;
		} else if (getLastVisiblePosition() == (getCount() - 1))
		{

			// 滑到底部了
//			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
//					&& getChildAt(
//							getLastVisiblePosition()
//									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())


				return isCanUp;
		}
		return false;
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		//滚动到底部有更多内容满屏才能执行上拉加载
		if((totalItemCount > visibleItemCount)&&getLastVisiblePosition()==totalItemCount-1){

			isCanUp=true;

		}
	}
}
