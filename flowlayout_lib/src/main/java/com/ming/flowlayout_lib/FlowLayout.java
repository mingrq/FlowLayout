package com.ming.flowlayout_lib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends RelativeLayout {
    //item
    private View itemView;
    //水平间距
    private int horizontalMargin;
    //垂直间距
    private int verticalMargin;
    //可选中item数量
    private int countAble;
    //单选
    private int SINGLECHOICE = 1;
    //多选
    private int MULTIPLECHOICE = 0;
    //数据集合
    private List dataList;
    //item点击监听
    private OnItemClickLienter onItemClickLienter;
    //item选择监听
    private OnItemSelectLisenter onItemSelectLisenter;
    //item选择集合
    List<Integer> selectList;
    private Context context;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化item选择集合
        selectList = new ArrayList<>();
        this.context = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).layout(l, t, r, b);
        }
    }

    /**
     * item点击监听
     */
    public interface OnItemClickLienter {
        void Click(int poistion);
    }

    /**
     * item选择监听
     */
    public interface OnItemSelectLisenter {
        void Select(List<Integer> selectPoistion);
    }
    /*--------------------------------------------------对外方法-----------------------------------------------------*/
    //-----------------------------初始化方法---------------------------------------

    /**
     * 设置item
     *
     * @param itemView
     * @return
     */
    public FlowLayout setItemView(View itemView) {
        this.itemView = itemView;
        return this;
    }

    /**
     * 设置间距
     *
     * @param horizontalMargin
     * @param verticalMargin
     * @return
     */
    public FlowLayout setItemMargin(int horizontalMargin, int verticalMargin) {
        this.horizontalMargin = horizontalMargin;
        this.verticalMargin = verticalMargin;
        return this;
    }

    /**
     * 设置选择状态
     * --多选|单选|限制数量
     *
     * @param countAble
     * @return
     */
    public FlowLayout setChoiceType(int countAble) throws InputException {
        if (countAble == 1) {//单选
            this.countAble = SINGLECHOICE;
        } else if (countAble == 0) {//多选
            this.countAble = MULTIPLECHOICE;
        } else if (countAble > 1) {//限制数量
            this.countAble = countAble;
        } else {
            //输入错误
            throw new InputException();
        }
        return this;
    }

    /**
     * 设置数据集合
     *
     * @param dataList
     * @return
     */
    public FlowLayout setDataList(List dataList) {
        this.dataList = dataList;
        return this;
    }

    /**
     * 设置item点击监听
     *
     * @param onItemClickLienter
     * @return
     */
    public FlowLayout setOnItemClickLienter(OnItemClickLienter onItemClickLienter) {
        this.onItemClickLienter = onItemClickLienter;
        return this;
    }

    /**
     * item选择监听
     *
     * @param onItemSelectLisenter
     * @return
     */
    public FlowLayout setOnItemSelectLisenter(OnItemSelectLisenter onItemSelectLisenter) {
        this.onItemSelectLisenter = onItemSelectLisenter;
        return this;
    }

    /**
     * 提交
     */
    public void commit() {
        //控件宽度
        int width = getMeasuredWidth();
        //item宽度和
        int widthsum = 0;
        for (int i = 0; i < getChildCount(); i++) {
            TextView textView = (TextView) getChildAt(i);
            RelativeLayout.LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
            if (i > 0)
                layoutParams.leftMargin=100;
                layoutParams.addRule(RelativeLayout.RIGHT_OF);
            textView.setLayoutParams(layoutParams);
            // widthsum += itemView.getMeasuredWidth() + horizontalMargin;
            //this.addView(itemView);
        }
    }
//--------------------------------------操作方法------------------------------------------------------

    /**
     * 获取item选择集合
     *
     * @return
     */
    public List<Integer> getSelectList() {
        return selectList;
    }

}
