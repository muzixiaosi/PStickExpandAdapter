package com.peng.pstickexpandadapter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peng.pstickexpandadapter.R;
import com.peng.pstickexpandadapter.model.CityGroupItemEntity;
import com.peng.pstickexpandadapter.model.OtherGroupItemEntity;
import com.peng.pstickexpandadapter.vh.OtherHeaderVH;
import com.peng.pstickexpandadapter.vh.OtherSubRvVH;
import com.peng.stickexpandadapter.expand.ExpandSectionEntity;
import com.peng.stickexpandadapter.expand.ExpandSectionIndexEntity;
import com.peng.stickexpandadapter.expand.ExpandStickAdapter;

import java.util.List;

/**
 * Created by lipeng on 2019-08-21.
 */
public class TestStickExpandComplexAdapter extends ExpandStickAdapter<CityGroupItemEntity> {

    public static final int VIEW_TYPE_HEADER = 11;
    public static final int VIEW_TYPE_OTHER_GROUP = 12;
    public static final int VIEW_TYPE_OTHER_SUB = 13;
    private List<OtherGroupItemEntity> mInsertSectionList;
    protected SparseArray<ExpandSectionIndexEntity> mInsertIndexMap;

    public TestStickExpandComplexAdapter() {
        super();
        mInsertIndexMap = new SparseArray<>();
    }

    public void setInsertData(List<OtherGroupItemEntity> list) {
        mInsertSectionList = list;
        mInsertIndexMap.clear();
        notifyDataSetChanged();
    }

    public List<OtherGroupItemEntity> getInsertData() {
        return mInsertSectionList;
    }

    @Override
    public boolean isStickPosition(int position) {
        return getItemViewType(position) == VIEW_TYPE_OTHER_GROUP
            || getItemViewType(position) == VIEW_TYPE_GROUP;
    }

    @Override
    protected int getAboveCount() {
        //0 header
        int count = 1;
        if (mInsertSectionList != null) {
            for (int i = 0; i < mInsertSectionList.size(); i++) {
                ExpandSectionEntity g = mInsertSectionList.get(i);
                count++;
                mInsertIndexMap.put(count - 1, new ExpandSectionIndexEntity(i, -1, g.childList == null ? 0 : g.childList.size()));
                if (g.childList != null && g.isExpand && g.childList.size() > 0) {
                    mInsertIndexMap.put(count, new ExpandSectionIndexEntity(i, 0, 1));
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        int count = 1;
        if (mInsertSectionList != null) {
            for (ExpandSectionEntity g : mInsertSectionList) {
                count++;
                if (position == count - 1) {
                    return VIEW_TYPE_OTHER_GROUP;
                }
                if (g.childList != null && g.isExpand && g.childList.size() > 0) {
                    count++;
                }
                if (position < count) {
                    return VIEW_TYPE_OTHER_SUB;
                }
            }
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                return new OtherHeaderVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_header, viewGroup, false));
            case VIEW_TYPE_OTHER_GROUP:
                return new GroupVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_group, viewGroup, false));
            case VIEW_TYPE_OTHER_SUB:
                return new OtherSubRvVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_sub_rv, viewGroup, false));
        }
        return super.onCreateViewHolder(viewGroup, viewType);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);

        if (getItemViewType(position) == VIEW_TYPE_OTHER_GROUP) {
            final int p = position;
            GroupVH vh = (GroupVH) viewHolder;
            int groupIndex = mInsertIndexMap.get(position).mGroupIndex;
            vh.mTvGroup.setText(mInsertSectionList.get(groupIndex).group);
            boolean isExpand = mInsertSectionList.get(groupIndex).isExpand;
            final boolean isCanExpand = mInsertSectionList.get(groupIndex).isCanExpand;

            vh.mImgViewArrow.setImageResource(isExpand ? R.drawable.down : R.drawable.right);
            vh.mImgViewArrow.setVisibility(isCanExpand ? View.VISIBLE : View.GONE);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCanExpand) {
                        switchOtherG(p);
                    }
                }
            });
        } else if (getItemViewType(position) == VIEW_TYPE_OTHER_SUB) {
            OtherSubRvVH vh = (OtherSubRvVH) viewHolder;
            int groupIndex = mInsertIndexMap.get(position).mGroupIndex;
            vh.bindData(mInsertSectionList.get(groupIndex).childList);
        }

    }

    public void switchOtherG(int position) {
        int groupIndex = mInsertIndexMap.get(position).mGroupIndex;
        if (mInsertSectionList.get(groupIndex).isCanExpand) {
            ExpandSectionEntity entity = mInsertSectionList.get(groupIndex);
            entity.isExpand = !entity.isExpand;
            notifyDataSetChanged();
        }
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
