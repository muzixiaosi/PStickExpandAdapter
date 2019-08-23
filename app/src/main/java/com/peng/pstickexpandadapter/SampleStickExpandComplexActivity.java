package com.peng.pstickexpandadapter;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.peng.pstickexpandadapter.adapter.TestStickExpandComplexAdapter;
import com.peng.pstickexpandadapter.model.CityGroupItemEntity;
import com.peng.pstickexpandadapter.model.OtherGroupItemEntity;
import com.peng.stickexpandadapter.SampleLinearItemDecoration;
import com.peng.stickexpandadapter.stick.StickHeaderItemDecoration;
import com.peng.stickexpandadapter.stick.StickHeaderTouchListener;

import java.util.ArrayList;
import java.util.List;

public class SampleStickExpandComplexActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TestStickExpandComplexAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mRecyclerView = findViewById(R.id.view_recycler);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expand_complex, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_expand:
                change(true);
                return true;
            case R.id.action_no_expand:
                change(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        int color = ContextCompat.getColor(this, R.color.divider_color);
        int dividerValue = 5;
        SampleLinearItemDecoration decor = new SampleLinearItemDecoration(color, dividerValue);
        mRecyclerView.addItemDecoration(decor);
        mRecyclerView.addItemDecoration(new StickHeaderItemDecoration());

        mAdapter = new TestStickExpandComplexAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //虚浮header 点击
        mRecyclerView.addOnItemTouchListener(new StickHeaderTouchListener(this, mRecyclerView,
            new StickHeaderTouchListener.OnHeaderClickListener() {
                @Override
                public void onClick(int position) {
                    Toast.makeText(SampleStickExpandComplexActivity.this, "stick header  click..." + position, Toast.LENGTH_SHORT).show();
                    int itemViewType = mAdapter.getItemViewType(position);
                    if (itemViewType == TestStickExpandComplexAdapter.VIEW_TYPE_OTHER_GROUP) {
                        mAdapter.switchOtherG(position);
                    } else {
                        mAdapter.switchExpand(position);
                    }
                }

                @Override
                public void onLongClick() {
                    Toast.makeText(SampleStickExpandComplexActivity.this, "header long click...", Toast.LENGTH_SHORT).show();
                }
            }));

        //模拟通用分组数据
        List<CityGroupItemEntity> normalSectionList = initSectionData();
        mAdapter.setData(normalSectionList);

        //模拟其他样式分组数据
        List<OtherGroupItemEntity> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            OtherGroupItemEntity o = new OtherGroupItemEntity();
            o.group = "水果" + i;
            o.isExpand = true;
            o.isCanExpand = false;

            List<String> subItems = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                subItems.add("apple" + j);
            }
            o.childList = subItems;
            list.add(o);
        }
        mAdapter.setInsertData(list);
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

    private void change(boolean isCanExpand) {
        List<OtherGroupItemEntity> list = mAdapter.getInsertData();
        if (list != null) {
            for (OtherGroupItemEntity o : list) {
                o.isCanExpand = isCanExpand;
            }
            mAdapter.setInsertData(list);
        }
    }
}
