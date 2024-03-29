package com.ming.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ming.flowlayout_lib.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.ming.flowlayout_lib.FlowLayout.MULTIPLECHOICE;
import static com.ming.flowlayout_lib.FlowLayout.RIGHT;


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

        flowLayout.setAdapter(testAdapter);
        flowLayout.setOnItemSelectedChangeLisenter(new FlowLayout.OnItemSelectedChangeLisenter() {
            @Override
            public void onSelectedChange(int position, boolean isChecked) {
                Log.e("onCheckedChange", String.valueOf(position));
            }

            @Override
            public void onSelected(int position) {
                Log.e("onChecked", String.valueOf(position));
            }

            @Override
            public void onUnSelected(int position) {
                Log.e("onUnChecked", String.valueOf(position));
            }
        });
        flowLayout.setOnItemClickLienter(new FlowLayout.OnItemClickLienter() {
            @Override
            public void onClick(int position, View itemView, FlowLayout flowLayout) {
                Log.e("onClick", String.valueOf(position));
            }
        });
        flowLayout.setSelectedEnable(true);
        flowLayout.setGravity(RIGHT);
        flowLayout.setMaxSelectedCount(MULTIPLECHOICE,3);
        flowLayout.setItemMargin(flowLayout.dp2px(10),19,15,50);
        flowLayout.commit();
        testAdapter.setDatas(jd);
        testAdapter.notifyDataChanged();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Log.e("ch",  flowLayout.getSelectedSet().toString());
            }
        };
        timer.schedule(timerTask,6000);
    }
}
