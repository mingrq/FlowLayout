package com.ming.flowlayout_lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
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
    private static final int SINGLECHOICE = 1;
    //多选
    private static final int MULTIPLECHOICE = 0;
    //数据集合
    private List dataList;
    //item点击监听
    private OnItemClickLienter onItemClickLienter;
    //item选择监听
    private OnItemCheckChangeLisenter onItemSelectLisenter;
    //item选择集合
    private List<Integer> selectList;
    //adapter
    private FlowlayoutAdapter flowlayoutAdapter;
    private Context context;

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
        //初始化item选择集合
        selectList = new ArrayList<>();
        //获取布局方向

        this.context = context;
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
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
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
        //行高
        int lineHeight = 0;
        //行宽
        int lineWidth = 0;
        //控件宽度
        int width = getWidth();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //child是否为隐藏状态，隐藏则设置下一个
            if (child.getVisibility() == View.GONE) continue;

            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            //child的宽高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
            //此行加上child宽后超过控件宽度

            }
          /*  int lc = left + lp.leftMargin;
            int tc = top + lp.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();
            child.layout();*/
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
    public interface OnItemCheckChangeLisenter {
        void Select(List<Integer> selectPoistion);
        void CheckChange(int postion);
    }
}
