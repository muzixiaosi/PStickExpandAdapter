package com.peng.pstickexpandadapter.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peng.pstickexpandadapter.R;

import java.util.List;

/**
 * Created by lipeng on 2019-08-23.
 */
public class OtherSubRvVH extends RecyclerView.ViewHolder {


    private final MyAdapter mMyAdapter;

    public OtherSubRvVH(@NonNull View itemView) {
        super(itemView);
        RecyclerView recyclerView = itemView.findViewById(R.id.item_sub_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
        mMyAdapter = new MyAdapter();
        recyclerView.setAdapter(mMyAdapter);
    }

    public void bindData(List<String> childList) {

        mMyAdapter.setData(childList);

    }

    static class MyAdapter extends RecyclerView.Adapter {

        private List<String> mChildList;

        public void setData(List<String> childList) {
            mChildList = childList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_sub_rv_child, viewGroup, false);
            return new MyVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            MyVH vh = (MyVH) viewHolder;
            vh.mTv.setText(mChildList.get(position));
        }

        @Override
        public int getItemCount() {
            return mChildList == null ? 0 : mChildList.size();
        }

    }

    static class MyVH extends RecyclerView.ViewHolder {

        private final TextView mTv;

        public MyVH(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);
        }
    }


}
