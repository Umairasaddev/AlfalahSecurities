package com.softech.bipldirect.Util;

import android.content.Context;

import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeableItemClickListener;

/**
 * Developed by Hasham.Tahir on 2/10/2016.
 */
public class FixSwipeableItemClickListener extends SwipeableItemClickListener {

    public FixSwipeableItemClickListener(Context context, OnItemClickListener listener) {
        super(context, listener);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}

