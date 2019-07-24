package com.ming.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ming.flowlayout_lib.FlowLayout;
import com.ming.flowlayout_lib.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlowLayout flowLayout = findViewById(R.id.ii);
        List<String> jd = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            jd.add("test" + i);
        }
        TestAdapter testAdapter = new TestAdapter(getBaseContext());
        testAdapter.setDatas(jd);
        flowLayout.setAdapter(testAdapter);
    }
}
