package com.peng.pstickexpandadapter;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peng.pstickexpandadapter.adapter.TestStickAdapter;
import com.peng.stickexpandadapter.SampleLinearItemDecoration;
import com.peng.stickexpandadapter.stick.StickHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SampleStickActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mRecyclerView = findViewById(R.id.view_recycler);
        showStick();
    }

    private void showStick() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        int color = ContextCompat.getColor(this, R.color.divider_color);
        int dividerValue = 5;
        SampleLinearItemDecoration decor = new SampleLinearItemDecoration(color, dividerValue);
        mRecyclerView.addItemDecoration(decor);
        mRecyclerView.addItemDecoration(new StickHeaderItemDecoration());

        TestStickAdapter adapter = new TestStickAdapter();
        mRecyclerView.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("item-" + i);
        }
        adapter.setData(list);
    }

}
