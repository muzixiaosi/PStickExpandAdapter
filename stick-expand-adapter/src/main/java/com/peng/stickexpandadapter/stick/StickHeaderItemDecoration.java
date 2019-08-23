package com.peng.stickexpandadapter.stick;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by lipeng on 2019-08-16.
 */
public class StickHeaderItemDecoration extends RecyclerView.ItemDecoration implements IStickHeaderDecoration {

    private Rect mStickHeaderRect = null;
    private int mStickHeaderPosition = -1;

    public StickHeaderItemDecoration() {
        super();
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //确保recyclerView 的 adapter是StickHeaderAdapter, 且有子view
        if (parent.getAdapter() instanceof StickHeaderAdapter && parent.getChildCount() > 0) {
            StickHeaderAdapter adapter = (StickHeaderAdapter) parent.getAdapter();


            //当前可见的第一个
            View firstView = parent.getChildAt(0);

            int firstAdapterPosition = parent.getChildAdapterPosition(firstView);

            int stickHeaderPosition = getStickHeaderPosition(adapter, firstAdapterPosition);
            mStickHeaderPosition = stickHeaderPosition;
            Log.d("tag@@", "stickHeaderPosition:" + stickHeaderPosition + ", adapter === " + adapter.toString());


            if (stickHeaderPosition != -1) {
                RecyclerView.ViewHolder stickHeaderVH = adapter.onCreateViewHolder(parent, adapter.getItemViewType(stickHeaderPosition));
                adapter.onBindViewHolder(stickHeaderVH, stickHeaderPosition);

                View stickHeaderView = stickHeaderVH.itemView;
                measureLayoutStickView(parent, stickHeaderView);

                int sectionStickOffset = 0;
                for (int i = 0; i < parent.getChildCount(); i++) {
                    if (adapter.isStickPosition(parent.getChildAdapterPosition(parent.getChildAt(i)))) {

                        View sectionView = parent.getChildAt(i);
                        int sectionTop = sectionView.getTop();
                        int stickViewHeight = stickHeaderView.getHeight();

                        if (sectionTop < stickViewHeight && sectionTop > 0) {
                            sectionStickOffset = sectionTop - stickViewHeight;
                        }
                    }
                }

                int saveCount = c.save();
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) stickHeaderView.getLayoutParams();
                if (layoutParams == null) {
                    throw new NullPointerException("StickHeaderItemDecoration...");
                }
                c.translate(layoutParams.leftMargin, sectionStickOffset);
                //c.clipRect(0, 0, recyclerView.getWidth(), stickHeaderView.getMeasuredHeight());
                stickHeaderView.draw(c);
                c.restoreToCount(saveCount);

                if (mStickHeaderRect == null) {
                    mStickHeaderRect = new Rect();
                }
                mStickHeaderRect.set(0, 0, parent.getWidth(), stickHeaderView.getMeasuredHeight() + sectionStickOffset);
            } else {
                mStickHeaderRect = null;

            }
        }
    }

    private int getStickHeaderPosition(StickHeaderAdapter adapter, int firstAdapterPosition) {
        int stickHeaderPosition = -1;
        for (int i = firstAdapterPosition; i >= 0; i--) {
            if (adapter.isStickPosition(i)) {
                return i;
            }
        }
        return stickHeaderPosition;
    }

    private void measureLayoutStickView(@NonNull RecyclerView parent, View stickView) {
        if (stickView.isLayoutRequested()) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) stickView.getLayoutParams();
            if (layoutParams == null) {
                throw new NullPointerException("StickHeaderItemDecoration...");
            }
            int widthSpec = View.MeasureSpec.makeMeasureSpec(
                parent.getMeasuredWidth() - layoutParams.leftMargin - layoutParams.rightMargin,
                View.MeasureSpec.EXACTLY
            );

            int heightSpec;
            if (layoutParams.height > 0) {
                heightSpec = View.MeasureSpec.makeMeasureSpec(
                    layoutParams.height,
                    View.MeasureSpec.EXACTLY
                );
            } else {
                heightSpec = View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                );
            }
            stickView.measure(widthSpec, heightSpec);
            stickView.layout(0, 0, stickView.getMeasuredWidth(), stickView.getMeasuredHeight());
        }
    }

    @Override
    public Rect getStickHeaderRect() {
        return mStickHeaderRect;
    }

    @Override
    public int getStickHeaderPosition() {
        return mStickHeaderPosition;
    }
}
