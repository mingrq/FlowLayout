package com.ming.flowlayout_lib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

public class FlowLayout extends TagFlowLayout
        implements FlowlayoutAdapter.OnDataChangedListener {
    //item
    private View itemView;
    //item左间距
    private int marginLeft;
    //item上间距
    private int marginTop;
    //item右间距
    private int marginRight;
    //item下间距
    private int marginBottom;
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

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public void onChanged() {
        addView();
    }

    /**
     * 将view加入flowlayout
     */
    private void addView() {
        removeAllViews();
        int itemCount = flowlayoutAdapter.getCount();
        for (int i = 0; i < itemCount; i++) {
            //输入的view
            View view = flowlayoutAdapter.getView(i, this);
            //重写的itemview，添加checked功能
            final ItemView itemView = new ItemView(getContext());
            //复制itemview的状态传递给所有clicked=false&&longclicked=false的子控件
            itemView.setDuplicateParentStateEnabled(true);
            if (view.getLayoutParams() != null && view.getLayoutParams() instanceof MarginLayoutParams) {
                //将view的布局参数赋予itemview
                itemView.setLayoutParams(view.getLayoutParams());
            } else {
                MarginLayoutParams mlp = new MarginLayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                mlp.setMargins(dp2px(0),
                        dp2px(0),
                        dp2px(0),
                        dp2px(0));
                itemView.setLayoutParams(mlp);
            }
            //重写为view设置布局参数，使之与item大小相同
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            //将view放入itemview中
            itemView.addView(view);
            //将itemview放入flowlayout中
            addView(itemView);

            //因为itemview使用了控件状态传递功能，将view的clicked设置成false
            view.setClickable(false);

            //为itemview设置点击监听
            final int position = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断点击监听是否存在
                    if (onItemClickLienter != null)
                        onItemClickLienter.onClick(position, itemView, FlowLayout.this);
                    //选中item
                    doSelect(itemView,position);
                }
            });
        }
    }

    /**
     * 选中iten
     * @param itemView 选中的view
     * @param postion  itemview在集合中的下标
     */
    private void doSelect(ItemView itemView,int postion){

    }

    /**
     * dp转px
     */
    public int dp2px(float dpValues) {
        dpValues = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValues, context.getResources().getDisplayMetrics());
        return (int) (dpValues + 0.5f);
    }

    //----------------------------------------监听器-----------------------------------------

    /**
     * item点击监听
     */
    public interface OnItemClickLienter {
        void onClick(int position, ItemView itemView, FlowLayout flowLayout);
    }

    /**
     * item选择监听
     */
    public interface OnItemCheckChangeLisenter {

        void onSelect(List<Integer> selectPosition);

        void onSelectChange(int position);
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
        //绑定数据变化监听
        flowlayoutAdapter.setOnDataChangedListener(this);
        addView();
        return this;
    }

    /**
     * 设置默认间距
     * --在item没有设置mragin时使用默认间距
     *
     * @param marginLeft   左间距
     * @param marginTop    上间距
     * @param marginRight  右间距
     * @param marginBottom 下间距
     * @return
     */
    public FlowLayout setItemDefultMargin(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        return this;
    }

    /**
     * 设置选择状态
     * --多选|单选|限制数量
     *
     * @param countAble
     * @return
     */
    public TagFlowLayout setMaxSelectCount(int countAble) throws InputException {
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
    public TagFlowLayout setOnItemSelectLisenter(OnItemCheckChangeLisenter onItemSelectLisenter) {
        this.onItemSelectLisenter = onItemSelectLisenter;
        return this;
    }

    /**
     * 设置预选中
     */
    public void setSelectedList(int... position) {

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
