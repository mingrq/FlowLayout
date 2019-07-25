package com.ming.flowlayout_lib;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class FlowlayoutAdapter<T> {

    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    private Context context;

    public FlowlayoutAdapter(Context context) {
        this.context = context;
    }

    /**
     * 数据变化监听接口
     */
    interface OnDataChangedListener {
        void onChanged();
    }

    /**
     * 设置数据变化监听
     * @param listener
     */
    protected void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    //--------------------------------------抽象方法--------------------------------------------------

    /**
     * 获取item的数量
     *
     * @return
     */
    public abstract int getCount();


    public abstract View getView(int position);



    //----------------------------------------对外方法------------------------------------------------

    /**
     * 更新数据
     */
    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    /**
     * 判断数据是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return getCount() == 0;
    }
}
