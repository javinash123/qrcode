package com.simprosys.scan.qrcode.barcode.reader.view;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by simprosys on 20/9/18.
 */

public class CustomViewPager extends ViewPager {
    private boolean swipeEnabled;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean getSwipeEnabled() {
        return swipeEnabled;
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeEnabled && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return swipeEnabled && super.canScrollHorizontally(direction);
    }
}
