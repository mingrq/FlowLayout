package com.ming.flowlayout;

import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
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
    public int getItem(int position) {
        return 0;
    }

    @Override
    public View getView(int position, FlowLayout parent) {
        TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item,
                parent, false);
        //TextView textView = new TextView(context);
        //textView.setTextColor(Color.BLACK);
        //textView.setBackgroundColor(Color.RED);
        textView.setText(mTagDatas.get(position));
        /*ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        Log.d("testw", String.valueOf(layoutParams.leftMargin));*/
        return textView;
    }
}
