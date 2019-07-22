package com.ming.flowlayout_lib;


import android.view.View;

import java.util.List;

public abstract class FlowlayoutAdapter<T> {
    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    public FlowlayoutAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    /**
     * 点击
     */
    public abstract void click();

    /**
     * 取消点击
     */
    public abstract void unclick();

    /**
     * 选择
     */
    public abstract void select();

    /**
     * 取消选择
     */
    public abstract void unselect();

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(TagFlowLayout parent, int position, T t);

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }
}
