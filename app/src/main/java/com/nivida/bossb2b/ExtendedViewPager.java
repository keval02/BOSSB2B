package com.nivida.bossb2b;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ExtendedViewPager extends ViewPager{
	private boolean pagingEnabled;
	Context context;


	public ExtendedViewPager(Context context) {
	    super(context);
	}

	public ExtendedViewPager(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    this.context = context;
	    pagingEnabled = true;
	}



	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		
	    if (v instanceof TouchImageView) {
	    	
	        return ((TouchImageView) v).canScrollHorizontallyFroyo(-dx);
	    } else {
	        return super.canScroll(v, checkV, dx, x, y);
	    }
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		
		
	}
	/*
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        if (pagingEnabled) {
           return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (pagingEnabled) {
           return super.onTouchEvent(event);
        }
        return false;
    }

    *//**
     * @param pagingEnabled
     *      if true, ViewPager acts as normal
     *      if false, ViewPager cannot be scrolled by a user's touch
     *//*
    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }*/
}
