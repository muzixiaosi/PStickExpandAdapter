package com.peng.stickexpandadapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lipeng on 2019-08-19.
 */
public class SampleGridItemDecoration extends RecyclerView.ItemDecoration {


    private int mSpacing;
    private boolean mIncludeEdge;
    private int mSpanCount;
    private final Paint mPaint;

    public SampleGridItemDecoration(Context context, int spanCount, int spacing, boolean includeEdge) {
        mSpanCount = spanCount;
        mSpacing = spacing;
        mIncludeEdge = includeEdge;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(context, R.color.divider_color));
    }

    public SampleGridItemDecoration(Context context, int spanCount, int spacing, boolean includeEdge, int dividerColor) {
        mSpanCount = spanCount;
        mSpacing = spacing;
        mIncludeEdge = includeEdge;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(dividerColor);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % mSpanCount; // item column

        if (mIncludeEdge) {
            outRect.left = mSpacing - column * mSpacing / mSpanCount; // mSpacing - column * ((1f / spanCount) * mSpacing)
            outRect.right = (column + 1) * mSpacing / mSpanCount; // (column + 1) * ((1f / spanCount) * mSpacing)

            if (position < mSpanCount) { // top edge
                outRect.top = mSpacing;
            }
            outRect.bottom = mSpacing; // item bottom
        } else {
            outRect.left = column * mSpacing / mSpanCount; // column * ((1f / spanCount) * mSpacing)
            outRect.right = mSpacing - (column + 1) * mSpacing / mSpanCount; // mSpacing - (column + 1) * ((1f /    spanCount) * mSpacing)
            if (position >= mSpanCount) {
                outRect.top = mSpacing; // item top
            }
        }

    }
}
