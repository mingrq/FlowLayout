package com.ming.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ming.flowlayout_lib.FlowLayout;
import com.ming.flowlayout_lib.ItemView;

import java.util.ArrayList;
import java.util.List;

import static com.ming.flowlayout_lib.FlowLayout.SINGLECHOICE;


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
        flowLayout.setOnItemCheckedChangeLisenter(new FlowLayout.OnItemCheckedChangeLisenter() {
            @Override
            public void onCheckedChange(int position, boolean isChecked) {
                Log.e("onCheckedChange", String.valueOf(position));
            }

            @Override
            public void onChecked(int position) {
                Log.e("onChecked", String.valueOf(position));
            }

            @Override
            public void onUnChecked(int position) {
                Log.e("onUnChecked", String.valueOf(position));
            }
        });
        flowLayout.setOnItemClickLienter(new FlowLayout.OnItemClickLienter() {
            @Override
            public void onClick(int position, ItemView itemView, FlowLayout flowLayout) {
                Log.e("onClick", String.valueOf(position));
            }
        });
       // flowLayout.setCheckedEnable(false);
        flowLayout.setMaxCheckCount(SINGLECHOICE,0);
        flowLayout.commit();
    }
}
