package com.android.biblio.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class DisabledViewPager extends ViewPager {

    public DisabledViewPager(Context context) {
        super(context);
    }

    public DisabledViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}
