package com.ming.flowlayout_lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

public class FlowLayout extends TagFlowLayout
        implements FlowlayoutAdapter.OnDataChangedListener {
    //item左间距
    private int marginLeft;
    //item上间距
    private int marginTop;
    //item右间距
    private int marginRight;
    //item下间距
    private int marginBottom;
    //单选
    public static final int SINGLECHOICE = 1;
    //多选
    public static final int MULTIPLECHOICE = 0;
    //可选中item数量
    private int maxSelectedCount = SINGLECHOICE;
    //item点击监听
    private OnItemClickLienter onItemClickLienter;
    //item选择监听
    private OnItemSelectedChangeLisenter onItemSelectedChangeLisenter;
    //adapter
    private FlowlayoutAdapter flowlayoutAdapter;
    private Context context;
    //item选中下标集合
    Set<Integer> selectedItemSet = new HashSet<>();

    //布局方向常量
    public static final int LEFT = -1;
    public static final int CENTER = 0;
    public static final int RIGHT = 1;

    //是否激活选择功能
    private boolean selectedEnable = true;
    //是否使用设置的item的margin
    private boolean isItemMarginEnable = false;

    //布局方向
    private int mGravity;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始清空集合
        selectedItemSet.clear();

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);

        //获取布局方向
        mGravity = typedArray.getInt(R.styleable.FlowLayout_flow_gravity, -1);

        //获取最大可选择的数量，默认单选
        maxSelectedCount = typedArray.getInt(R.styleable.FlowLayout_max_select, SINGLECHOICE);
        //获取是否激活选中功能
        setSelectedEnable(typedArray.getBoolean(R.styleable.FlowLayout_isCheckedEnable, true));
        //设置布局方向
        setGravity(mGravity);

        //回收属性集合
        typedArray.recycle();
    }

    @Override
    public void onChanged() {
        dataViewChange();
    }

    /**
     * 将view加入flowlayout
     */
    private void dataViewChange() {
        removeAllViews();
        int itemCount = flowlayoutAdapter.getCount();
        for (int i = 0; i < itemCount; i++) {
            //输入的view
            final View view = flowlayoutAdapter.getView(i, this);
            //将itemview放入flowlayout中
            if (!isItemMarginEnable && view.getLayoutParams() != null && view.getLayoutParams() instanceof MarginLayoutParams) {
                //在没有设置item的margin值 并且view的布局参数不为空 并且 布局参数是MarginLayoutParams的子类

                //将view的布局参数赋予itemview
                view.setLayoutParams(view.getLayoutParams());
            } else {
                MarginLayoutParams mlp = new MarginLayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                mlp.setMargins(marginLeft,
                        marginTop,
                        marginRight,
                        marginBottom);
                view.setLayoutParams(mlp);
            }
            addView(view);

            //设置预选中状态
            if (selectedEnable)//是否激活选择功能
                if (selectedItemSet.contains(i)) {
                    view.setSelected(true);
                }

            //为itemview设置点击监听
            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断点击监听是否存在
                    if (onItemClickLienter != null)
                        onItemClickLienter.onClick(position, view, FlowLayout.this);
                    //处理选中的item点击事件
                    if (selectedEnable)//是否激活选择功能
                        doSelected(view, position);
                }
            });
        }
    }

    /**
     * 处理选中的item点击事件
     *
     * @param itemView 选中的view
     * @param postion  itemview在集合中的下标
     */
    private void doSelected(View itemView, int postion) {
        if (itemView.isSelected()) {
            //选中状态，设置为未选中状态
            setChildUnSelected(itemView, postion);
        } else {
            //未选中状态，设置成选中状态

            if (maxSelectedCount == SINGLECHOICE && selectedItemSet.size() >= SINGLECHOICE) {
                //单选

                //获取集合中选中的item的下标
                Integer itemindex = selectedItemSet.iterator().next();
                //获取item
                View checkItem = getChildAt(itemindex);
                setChildUnSelected(checkItem, itemindex);
            } else if (maxSelectedCount != MULTIPLECHOICE && selectedItemSet.size() == maxSelectedCount) {
                //多选--规定数量

                //选中数量到达设置数量，不进行操作了
                return;
            }
            setChildSelected(itemView, postion);
        }
        if (onItemSelectedChangeLisenter != null)
            onItemSelectedChangeLisenter.onSelectedChange(postion, itemView.isSelected());
    }


    /**
     * 选中item
     *
     * @param itemView 选中的view
     * @param postion  itemview在集合中的下标
     */
    private void setChildSelected(View itemView, int postion) {
        //设置item为选中状态
        itemView.setSelected(true);
        //将选中的item的下标添加到集合中
        selectedItemSet.add(postion);
        if (onItemSelectedChangeLisenter != null)
            onItemSelectedChangeLisenter.onSelected(postion);
    }

    /**
     * 取消选中item
     *
     * @param itemView 选中的view
     * @param postion  itemview在集合中的下标
     */
    private void setChildUnSelected(View itemView, int postion) {
        //设置item为未选中状态
        itemView.setSelected(false);
        //将选中的item的下标在集合中移除
        selectedItemSet.remove(postion);
        if (onItemSelectedChangeLisenter != null)
            onItemSelectedChangeLisenter.onUnSelected(postion);
    }


    //----------------------------------------监听器-----------------------------------------

    /**
     * item点击监听
     */
    public interface OnItemClickLienter {
        void onClick(int position, View itemView, FlowLayout flowLayout);
    }

    /**
     * item选择监听
     */
    public interface OnItemSelectedChangeLisenter {

        void onSelectedChange(int position, boolean isChecked);

        void onSelected(int position);

        void onUnSelected(int position);
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
     * 设置布局方向
     *
     * @param gravity 布局方向 LEFT CENTER RIGHT
     * @return
     */
    public FlowLayout setGravity(int gravity) {
        super.setGravity(gravity);
        return this;
    }

    /**
     * 设置默认间距
     *
     * @param marginLeft   左间距
     * @param marginTop    上间距
     * @param marginRight  右间距
     * @param marginBottom 下间距
     * @return
     */
    public FlowLayout setItemMargin(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        //使用设置的参数作为item的margin值
        isItemMarginEnable = true;

        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        return this;
    }

    /**
     * 是否激活选择功能
     *
     * @param selectedEnable
     */
    public TagFlowLayout setSelectedEnable(boolean selectedEnable) {
        this.selectedEnable = selectedEnable;
        return this;
    }

    /**
     * 设置选择状态
     * --多选|单选|限制数量
     *
     * @param maxSelectedCount 最大选中数量
     * @param position         预选中下标
     * @return
     */
    public TagFlowLayout setMaxSelectedCount(int maxSelectedCount, int... position) throws MaxSelectedInputException {
        this.maxSelectedCount = maxSelectedCount;
        if (position != null) {
            //获取预选中的下标数组
            int[] reserveSelected = position;
            //遍历数组
            for (int p : reserveSelected) {
                selectedItemSet.add(p);
            }

            //判断与选定数量是否超出可选数量，超出报错
            if (maxSelectedCount != 0 && selectedItemSet.size() > this.maxSelectedCount) {
                MaxSelectedInputException exception = new MaxSelectedInputException();
                throw exception;
            }
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
     * @param onItemSelectedChangeLisenter
     * @return
     */
    public TagFlowLayout setOnItemSelectedChangeLisenter(OnItemSelectedChangeLisenter onItemSelectedChangeLisenter) {
        this.onItemSelectedChangeLisenter = onItemSelectedChangeLisenter;
        return this;
    }

    /**
     * 提交
     */
    public void commit() {
        //绑定数据变化监听
        flowlayoutAdapter.setOnDataChangedListener(this);
        dataViewChange();

    }

    /**
     * dp转px
     */
    public int dp2px(float dpValues) {
        dpValues = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValues, context.getResources().getDisplayMetrics());
        return (int) (dpValues + 0.5f);
    }

//--------------------------------------操作方法------------------------------------------------------

    /**
     * 获取item选择集合
     *
     * @return
     */
    public Set<Integer> getSelectedSet() {
        return selectedItemSet;
    }
}
