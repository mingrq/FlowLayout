package com.ming.flowlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ming.flowlayout_lib.FlowLayout;
import com.ming.flowlayout_lib.FlowlayoutAdapter;
import com.ming.flowlayout_lib.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowLayout = findViewById(R.id.ii);

        final List<String> jd = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            jd.add("test" + (i + 1));
        }
        TestAdapter testAdapter = new TestAdapter(getBaseContext());
        testAdapter.setDatas(jd);
        flowLayout.setAdapter(testAdapter);
    }
}
