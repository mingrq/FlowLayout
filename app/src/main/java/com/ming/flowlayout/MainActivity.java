package com.ming.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ming.flowlayout_lib.FlowLayout;
import java.util.ArrayList;
import java.util.List;

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
        flowLayout.setReserveCheckedList(0,2,5);
        flowLayout.setAdapter(testAdapter);
        flowLayout.setOnItemCheckedChangeLisenter(new FlowLayout.OnItemCheckedChangeLisenter() {
            @Override
            public void onCheckedChange(int position, boolean isChecked) {
                Toast.makeText(getBaseContext(),"onCheckedChange: "+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChecked(int position) {
                Toast.makeText(getBaseContext(),"onChecked: "+position,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onUnChecked(int position) {
                Toast.makeText(getBaseContext(),"onUnChecked: "+position,Toast.LENGTH_SHORT).show();

            }
        });

    }
}
