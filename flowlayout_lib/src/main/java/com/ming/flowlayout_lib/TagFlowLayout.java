package com.ming.flowlayout_lib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagFlowLayout extends ViewGroup {
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
    private List<Integer> selectList;
    //adapter
    private FlowlayoutAdapter flowlayoutAdapter;
    private Context context;
    private int count;

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化item选择集合
        selectList = new ArrayList<>();
        Log.d("test",String.valueOf(LayoutDirection.RTL));
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //控件宽
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        //控件宽模式
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        //控件高
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //控件高模式
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //获取child数量
        int cCount = getChildCount();

        //所有显示child的宽和高
        int width = 0;
        int height = 0;

        //单个child的宽和高
        int lineWidth = 0;
        int lineHeight = 0;

        //遍历所有child
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                //child是隐藏的
                if (i == cCount - 1) {

                    //最后一个child
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                }
                //开始下一循环
                continue;
            }
            //测量child
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            ViewGroup.MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            //child的宽和高
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //测量child宽高确定View的宽高
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //现有的child宽大于控件宽
                width = Math.max(lineWidth, width);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //现有的child宽小于控件宽
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, height);
            }
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingRight() + getPaddingLeft(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //child数量
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //child是否为隐藏状态，隐藏则设置下一个
            if (child.getVisibility() == View.GONE) continue;


            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();


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
     * 设置适配器
     *
     * @param flowlayoutAdapter
     * @return
     */
    public TagFlowLayout setAdapter(FlowlayoutAdapter flowlayoutAdapter) {
        this.flowlayoutAdapter = flowlayoutAdapter;
        return this;
    }

    /**
     * 设置间距
     *
     * @param horizontalMargin
     * @param verticalMargin
     * @return
     */
    public TagFlowLayout setItemMargin(int horizontalMargin, int verticalMargin) {
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
    public TagFlowLayout setChoiceType(int countAble) throws InputException {
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
     * 设置item点击监听
     *
     * @param onItemClickLienter
     * @return
     */
    public TagFlowLayout setOnItemClickLienter(OnItemClickLienter onItemClickLienter) {
        this.onItemClickLienter = onItemClickLienter;
        return this;
    }

    /**
     * item选择监听
     *
     * @param onItemSelectLisenter
     * @return
     */
    public TagFlowLayout setOnItemSelectLisenter(OnItemSelectLisenter onItemSelectLisenter) {
        this.onItemSelectLisenter = onItemSelectLisenter;
        return this;
    }

    /**
     * 设置预选中
     */
    public void setSelectedList(int... postion) {

    }

    /**
     * 提交
     */
    public void commit() {

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
