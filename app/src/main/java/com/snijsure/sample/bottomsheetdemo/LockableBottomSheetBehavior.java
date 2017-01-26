package com.snijsure.sample.bottomsheetdemo;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class LockableBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {
    int startState;
    public LockableBottomSheetBehavior() {}

    public LockableBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        return super.onInterceptTouchEvent(parent, child, event);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {


        int action = MotionEventCompat.getActionMasked(event);
        if(action == MotionEvent.ACTION_DOWN) {
            startState = getState();
        }
        if(action == MotionEvent.ACTION_UP ) {
            if( getState() == BottomSheetBehavior.STATE_DRAGGING && startState == BottomSheetBehavior.STATE_COLLAPSED) {
                Rect scrollBounds = new Rect();
                child.getGlobalVisibleRect(scrollBounds); //Should we set height of
                setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            else if (getState() == BottomSheetBehavior.STATE_DRAGGING && startState == BottomSheetBehavior.STATE_EXPANDED ) {
                setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            return true;
        }
        return super.onTouchEvent(parent, child, event);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {

        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);

    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);

    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }
}
