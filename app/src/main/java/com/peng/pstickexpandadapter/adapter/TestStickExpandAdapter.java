package com.peng.pstickexpandadapter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peng.pstickexpandadapter.R;
import com.peng.pstickexpandadapter.model.CityGroupItemEntity;
import com.peng.stickexpandadapter.expand.ExpandStickAdapter;

/**
 * Created by lipeng on 2019-08-21.
 */
public class TestStickExpandAdapter extends ExpandStickAdapter<CityGroupItemEntity> {

    @Override
    public boolean isStickPosition(int position) {
        return getItemViewType(position) == VIEW_TYPE_GROUP;
    }

    @Override
    protected int getAboveCount() {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateGroupVH(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_group, viewGroup, false);
        return new GroupVH(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSubVH(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_sub, viewGroup, false);
        return new SubItemVH(view);
    }

    @Override
    protected void onBindGroupVH(RecyclerView.ViewHolder viewHolder, int position) {
        final int p = position;
        GroupVH vh = (GroupVH) viewHolder;
        int groupIndex = mIndexMap.get(position).mGroupIndex;
        vh.mTvGroup.setText(mExpandSectionList.get(groupIndex).group);
        boolean isExpand = mExpandSectionList.get(groupIndex).isExpand;
        final boolean isCanExpand = mExpandSectionList.get(groupIndex).isCanExpand;

        vh.mImgViewArrow.setImageResource(isExpand ? R.drawable.down : R.drawable.right);
        vh.mImgViewArrow.setVisibility(isCanExpand ? View.VISIBLE : View.GONE);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanExpand) {
                    switchExpand(p);
                }
            }
        });
    }

    @Override
    protected void onBindSubVH(RecyclerView.ViewHolder viewHolder, int position) {
        SubItemVH subItemVH = (SubItemVH) viewHolder;
        int groupIndex = mIndexMap.get(position).mGroupIndex;
        int childIndex = mIndexMap.get(position).mChildIndex;
        subItemVH.mTvSub.setText(mExpandSectionList.get(groupIndex).childList.get(childIndex));
    }

    static class GroupVH extends RecyclerView.ViewHolder {
        TextView mTvGroup;
        ImageView mImgViewArrow;

        public GroupVH(@NonNull View itemView) {
            super(itemView);
            mTvGroup = itemView.findViewById(R.id.tv_group);
            mImgViewArrow = itemView.findViewById(R.id.view_arrow);
        }
    }

    static class SubItemVH extends RecyclerView.ViewHolder {
        TextView mTvSub;

        public SubItemVH(@NonNull View itemView) {
            super(itemView);
            mTvSub = itemView.findViewById(R.id.tv_sub);
        }
    }

}
