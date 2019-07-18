package com.ming.flowlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ming.flowlayout_lib.FlowLayout;
import com.ming.flowlayout_lib.ItemViewImp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlowLayout flowLayout = findViewById(R.id.ii);
        List jd = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setText("test" + i);
            //textView.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
            //textView.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
            textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            flowLayout.addView(textView);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            if (i > 0)
                layoutParams.leftMargin = 100;
            layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.addRule(RelativeLayout.RIGHT_OF, i - 1);
        }
        //flowLayout.commit();
    }
}
