package com.peng.stickexpandadapter.expand;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.peng.stickexpandadapter.stick.StickHeaderAdapter;

import java.util.List;

/**
 * Created by lipeng on 2019-08-21.
 */
public abstract class ExpandStickAdapter<T extends ExpandSectionEntity> extends StickHeaderAdapter {

    protected List<T> mExpandSectionList;
    protected static final int VIEW_TYPE_GROUP = 0;
    protected static final int VIEW_TYPE_SUB = 1;
    protected SparseArray<ExpandSectionIndexEntity> mIndexMap;

    public ExpandStickAdapter() {
        mIndexMap = new SparseArray<>();
    }

    public void setData(List<T> list) {
        mExpandSectionList = list;
        mIndexMap.clear();
        notifyDataSetChanged();
    }

    public void switchExpand(int adapterPosition) {
        int groupIndex = mIndexMap.get(adapterPosition).mGroupIndex;
        ExpandSectionEntity entity = mExpandSectionList.get(groupIndex);
        entity.isExpand = !entity.isExpand;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = getAboveCount();
        if (mExpandSectionList == null) {
            return count;
        }
        for (int i = 0; i < mExpandSectionList.size(); i++) {
            ExpandSectionEntity g = mExpandSectionList.get(i);
            count++;
            mIndexMap.put(count - 1, new ExpandSectionIndexEntity(i, -1, g.childList == null ? 0 : g.childList.size()));
            int childStartPosition = count;
            if (g.childList != null && g.isExpand) {
                count += g.childList.size();
            }
            int childEndPosition = count;
            for (int j = childStartPosition; j < childEndPosition; j++) {
                mIndexMap.put(j, new ExpandSectionIndexEntity(i, j - childStartPosition, g.childList == null ? 0 : g.childList.size()));
            }
        }
        return count;
    }

    protected abstract int getAboveCount();

    @Override
    public int getItemViewType(int position) {
        int count = getAboveCount();
        for (ExpandSectionEntity g : mExpandSectionList) {
            count++;
            if (position == count - 1) {
                return VIEW_TYPE_GROUP;
            }
            if (g.childList != null && g.isExpand) {
                count += g.childList.size();
            }
            if (position < count) {
                return VIEW_TYPE_SUB;
            }
        }
        throw new IllegalArgumentException("getItemViewType exception");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_GROUP) {
            return onCreateGroupVH(viewGroup, viewType);
        } else {
            return onCreateSubVH(viewGroup, viewType);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateGroupVH(ViewGroup viewGroup, int viewType);

    protected abstract RecyclerView.ViewHolder onCreateSubVH(ViewGroup viewGroup, int viewType);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == VIEW_TYPE_GROUP) {
            onBindGroupVH(viewHolder, position);
        } else if (itemViewType == VIEW_TYPE_SUB) {
            onBindSubVH(viewHolder, position);
        }
    }

    protected abstract void onBindGroupVH(RecyclerView.ViewHolder viewHolder, int position);

    protected abstract void onBindSubVH(RecyclerView.ViewHolder viewHolder, int position);

}
