package com.ming.flowlayout_lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
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
    private static final int SINGLECHOICE = 1;
    //多选
    private static final int MULTIPLECHOICE = 0;
    //数据集合
    private List dataList;

    //item选择集合
    private List<Integer> selectList;
    //adapter
    private FlowlayoutAdapter flowlayoutAdapter;
    private Context context;

    //单行view集合
    List<View> lineViewList = new ArrayList<>();
    //所有存放view的行集合
    List<List<View>> allLineViewList = new ArrayList<>();
    //存放每行的宽度集合
    List<Integer> lineWidthList = new ArrayList<>();
    //布局方向
    private int mGravity;
    //布局方向常量
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        //初始化item选择集合
        selectList = new ArrayList<>();

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);

        //获取最大可选数量
        countAble = typedArray.getInt(R.styleable.FlowLayout_max_select, SINGLECHOICE);

        //获取布局方向
        mGravity = typedArray.getInt(R.styleable.FlowLayout_tag_gravity, LEFT);

        typedArray.recycle();
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

        //所有显示child的宽和高
        int width = 0;
        int height = 0;

        //单个child的宽和高
        int lineWidth = 0;
        int lineHeight = 0;

        //获取child数量
        int cCount = getChildCount();


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
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //child的宽和高
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //测量child宽高确定View的宽高
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //现有的所有child宽相加大于控件宽
                width = Math.max(lineWidth, width);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //现有的所有child宽相加小于控件宽
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
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
        //清空集合
        lineViewList.clear();
        allLineViewList.clear();

        //child数量
        int cCount = getChildCount();
        //行高
        int lineHeight = 0;
        //行宽
        int lineWidth = 0;
        //控件宽度
        int width = getWidth();

        //遍历child，确定位置
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //child是否为隐藏状态，隐藏则设置下一个
            if (child.getVisibility() == View.GONE)
                continue;

            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            //child的宽高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //确定itemview的行位置，第一个view不会执行这个循环(防止第一个child就大于控件宽)
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight() && i > 0) {
                //此行加上child宽后超过控件宽度
                //开始下一行计算

                //记录这一行的宽度，在使用布局方向时使用
                lineWidthList.add(lineWidth);

                //清空行宽，重新计算
                lineWidth = 0;
                //计算行高
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;

                //将这一行的view集合放入所有view集合中，新建一个view集合
                allLineViewList.add(lineViewList);
                lineViewList = new ArrayList<>();
            }

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            //所有行中最高值
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViewList.add(child);
        }
        allLineViewList.add(lineViewList);

        //记录这一行的宽度，在使用布局方向时使用
        lineWidthList.add(lineWidth);

        //view左上角位置
        int leftDimension = getPaddingLeft();
        int topDimension = getPaddingTop();

        //遍历所有行集合
        for (int j = 0; j < allLineViewList.size(); j++) {
            //获取行view集合
            lineViewList = allLineViewList.get(j);
            int currentLineWidth;
            switch (mGravity) {
                case LEFT://item靠左布局
                    leftDimension = getPaddingLeft();
                    break;
                case CENTER://item居中布局
                    currentLineWidth = lineWidthList.get(j);
                    leftDimension = (width-currentLineWidth)/2;
                    break;
                case RIGHT://item靠右布局
                    currentLineWidth = lineWidthList.get(j);
                    leftDimension = width - getPaddingRight() - currentLineWidth;
                    break;
            }
            currentLineWidth = lineWidthList.get(j);
            leftDimension = (width-currentLineWidth)/2;
            //遍历行行view集合
            for (int k = 0; k < lineViewList.size(); k++) {
                View child = lineViewList.get(k);

                //child是否为隐藏状态，隐藏则设置下一个
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = leftDimension + lp.leftMargin;
                int tc = topDimension + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);
                leftDimension = rc + lp.rightMargin;
            }
            topDimension += lineHeight;
        }
    }


    //--------------------------------设置LayoutParams------------------------------------------
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

}
