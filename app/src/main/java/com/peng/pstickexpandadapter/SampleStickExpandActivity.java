package com.peng.pstickexpandadapter;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.peng.pstickexpandadapter.adapter.TestStickExpandAdapter;
import com.peng.pstickexpandadapter.model.CityGroupItemEntity;
import com.peng.stickexpandadapter.SampleLinearItemDecoration;
import com.peng.stickexpandadapter.stick.StickHeaderItemDecoration;
import com.peng.stickexpandadapter.stick.StickHeaderTouchListener;

import java.util.ArrayList;
import java.util.List;

public class SampleStickExpandActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mRecyclerView = findViewById(R.id.view_recycler);

        showStickExpand();
    }

    private void showStickExpand() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        int color = ContextCompat.getColor(this, R.color.divider_color);
        int dividerValue = 5;
        SampleLinearItemDecoration decor = new SampleLinearItemDecoration(color, dividerValue);
        mRecyclerView.addItemDecoration(decor);
        mRecyclerView.addItemDecoration(new StickHeaderItemDecoration());
        final TestStickExpandAdapter adapter = new TestStickExpandAdapter();
        mRecyclerView.setAdapter(adapter);

        //虚浮header 点击
        mRecyclerView.addOnItemTouchListener(new StickHeaderTouchListener(this, mRecyclerView,
            new StickHeaderTouchListener.OnHeaderClickListener() {
                @Override
                public void onClick(int position) {
                    Toast.makeText(SampleStickExpandActivity.this, "stick header  click..." + position, Toast.LENGTH_SHORT).show();
                    adapter.switchExpand(position);
                }

                @Override
                public void onLongClick() {
                    Toast.makeText(SampleStickExpandActivity.this, "header long click...", Toast.LENGTH_SHORT).show();
                }
            }));


        //模拟通用分组数据
        List<CityGroupItemEntity> normalSectionList = initSectionData();
        adapter.setData(normalSectionList);
    }

    private List<CityGroupItemEntity> initSectionData() {
        List<CityGroupItemEntity> entityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CityGroupItemEntity entity = new CityGroupItemEntity();
            entity.isExpand = true;
            entity.isCanExpand = true;
            entity.group = "省份" + i;
            List<String> subItems = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                subItems.add("城市" + j);
            }
            entity.childList = subItems;
            entityList.add(entity);
        }
        return entityList;
    }
}
