package com.peng.pstickexpandadapter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peng.pstickexpandadapter.R;
import com.peng.stickexpandadapter.stick.StickHeaderAdapter;

import java.util.List;

/**
 * Created by lipeng on 2019-08-16.
 */
public class TestStickAdapter extends StickHeaderAdapter<TestStickAdapter.TestVH> {

    private List<String> mStringList;

    public void setData(List<String> stringList) {
        mStringList = stringList;
        notifyDataSetChanged();
    }

    @Override
    public boolean isStickPosition(int position) {
        return position % 3 == 0;
    }

    @NonNull
    @Override
    public TestVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_test, viewGroup, false);
        return new TestVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestVH testVH, int i) {
        testVH.bindData(mStringList.get(i));
    }

    @Override
    public int getItemCount() {
        return mStringList == null ? 0 : mStringList.size();
    }

    static class TestVH extends RecyclerView.ViewHolder {

        private final TextView mTextView;

        public TestVH(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_tv);
        }

        public void bindData(String s) {
            mTextView.setText(s);
        }
    }

}
