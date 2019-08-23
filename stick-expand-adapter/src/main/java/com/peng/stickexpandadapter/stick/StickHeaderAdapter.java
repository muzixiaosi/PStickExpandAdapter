package com.peng.stickexpandadapter.stick;

import android.support.v7.widget.RecyclerView;

/**
 * Created by lipeng on 2019-08-16.
 */
public abstract class StickHeaderAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    /**
     * 该position 对应位置是否固定
     */
    public abstract boolean isStickPosition(int position);

}
