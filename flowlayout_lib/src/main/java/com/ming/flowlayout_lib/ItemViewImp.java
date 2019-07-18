package com.ming.flowlayout_lib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

public class ItemViewImp extends android.support.v7.widget.AppCompatTextView implements IitemView {

    public ItemViewImp(Context context) {
        this(context, null);
    }

    public ItemViewImp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemViewImp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void click() {
        Log.d("test","点击");
    }

    @Override
    public void unclick() {
        Log.d("test","取消点击");
    }

    @Override
    public void select() {
        Log.d("test","选择");
    }

    @Override
    public void unselect() {
        Log.d("test","取消选择");
    }
}
