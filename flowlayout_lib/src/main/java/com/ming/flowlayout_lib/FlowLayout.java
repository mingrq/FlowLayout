package com.ming.flowlayout_lib;

import android.content.Context;
import android.util.AttributeSet;

public class FlowLayout extends TagFlowLayout implements FlowlayoutAdapter.OnDataChangedListener {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onChanged() {

    }
}
