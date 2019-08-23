package com.peng.stickexpandadapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lipeng on 2019-08-16.
 */
public class SampleLinearItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private int mDividerValue;

    public SampleLinearItemDecoration(int color, int dividerValue) {
        super();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mDividerValue = dividerValue;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        float left = parent.getPaddingLeft();
        float right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float recTop = view.getBottom();
            float recBottom = recTop + mDividerValue;
            c.drawRect(left, recTop, right, recBottom, mPaint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null) {
            int itemCount = adapter.getItemCount();
            int childAdapterPosition = parent.getChildAdapterPosition(view);

            if (childAdapterPosition < itemCount - 1) {
                outRect.bottom = mDividerValue;
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }
}
