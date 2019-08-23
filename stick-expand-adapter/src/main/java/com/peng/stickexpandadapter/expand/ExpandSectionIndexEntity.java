package com.peng.stickexpandadapter.expand;

/**
 * RecyclerView adapter 里面每个position对应的信息
 */
public class ExpandSectionIndexEntity {

	public int mGroupIndex;
	public int mChildIndex;
	public int mChildCount;

	public ExpandSectionIndexEntity(int groupIndex, int childIndex, int childCount) {
		mGroupIndex = groupIndex;
		mChildIndex = childIndex;
		mChildCount = childCount;
	}

}
