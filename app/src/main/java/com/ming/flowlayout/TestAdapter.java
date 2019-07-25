package com.ming.flowlayout;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ming.flowlayout_lib.FlowLayout;
import com.ming.flowlayout_lib.FlowlayoutAdapter;
import com.ming.flowlayout_lib.TagFlowLayout;

import java.util.List;

public class TestAdapter extends FlowlayoutAdapter {
    private List<String> mTagDatas;
    Context context;

    public TestAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public void setDatas(List<String> datas) {
        mTagDatas = datas;
    }

    @Override
    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    @Override
    public View getView(int position) {
        View view = View.inflate(context, R.layout.item, null);
        TextView textView = view.findViewById(R.id.tesr);
        textView.setTextColor(Color.BLACK);
        textView.setText(mTagDatas.get(position));
        return view;
    }
}
