package com.ming.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ming.flowlayout_lib.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TagFlowLayout flowLayout = findViewById(R.id.ii);
        flowLayout.setItemMargin(10,0);
        List jd = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setText("test" + i);
            textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            flowLayout.addView(textView);
        }
    }
}
