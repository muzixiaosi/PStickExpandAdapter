package com.peng.stickexpandadapter.stick;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.peng.stickexpandadapter.stick.IStickHeaderDecoration;

/**
 * Created by lipeng on 2019-08-20.
 */
public class StickHeaderTouchListener implements RecyclerView.OnItemTouchListener {


    private Context mContext;
    private RecyclerView mRecyclerView;
    private OnHeaderClickListener mListener;
    private GestureDetector mGestureDetector;

    public interface OnHeaderClickListener {
        void onClick(int position);

        void onLongClick();
    }

    public StickHeaderTouchListener(Context context, RecyclerView recyclerView, OnHeaderClickListener listener) {
        mContext = context;
        mRecyclerView = recyclerView;
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mListener == null) {
                    return false;
                }
                IStickHeaderDecoration stickHeaderInterface = getStickHeaderDecoration(mRecyclerView);
                if (stickHeaderInterface == null) {
                    return false;
                }
                Rect stickHeaderRect = stickHeaderInterface.getStickHeaderRect();
                int stickHeaderPosition = stickHeaderInterface.getStickHeaderPosition();
                if (stickHeaderRect == null || stickHeaderPosition == -1) {
                    return false;
                }
                if (stickHeaderRect.contains((int) e.getX(), (int) e.getY())) {
                    mListener.onClick(stickHeaderPosition);
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (mListener == null) {
                    return;
                }
                IStickHeaderDecoration stickHeaderInterface = getStickHeaderDecoration(mRecyclerView);
                if (stickHeaderInterface == null) {
                    return;
                }
                Rect stickHeaderRect = stickHeaderInterface.getStickHeaderRect();
                int stickHeaderPosition = stickHeaderInterface.getStickHeaderPosition();
                if (stickHeaderRect == null || stickHeaderPosition == -1) {
                    return;
                }
                if (stickHeaderRect.contains((int) e.getX(), (int) e.getY())) {
                    mListener.onLongClick();
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent e) {
        if (this.mListener != null) {
            boolean tapDetectorResponse = mGestureDetector.onTouchEvent(e);
            if (tapDetectorResponse) {
                return true;
            }

            IStickHeaderDecoration stickHeaderInterface = getStickHeaderDecoration(recyclerView);
            if (stickHeaderInterface == null) {
                return false;
            }
            Rect stickHeaderRect = stickHeaderInterface.getStickHeaderRect();
            int stickHeaderPosition = stickHeaderInterface.getStickHeaderPosition();
            if (stickHeaderRect == null || stickHeaderPosition == -1) {
                return false;
            }
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                return stickHeaderRect.contains((int) e.getX(), (int) e.getY());
            }
        }
        return false;


    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        if (mListener != null) {
            mGestureDetector.onTouchEvent(motionEvent);
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) { }

    public IStickHeaderDecoration getStickHeaderDecoration(RecyclerView recyclerView) {
        int decorationIndex = 0;
        RecyclerView.ItemDecoration itemDecoration;
        do {
            itemDecoration = recyclerView.getItemDecorationAt(decorationIndex);
            if (itemDecoration instanceof IStickHeaderDecoration) {
                return (IStickHeaderDecoration) itemDecoration;
            }
            decorationIndex++;

        } while (itemDecoration != null);
        return null;
    }

}
