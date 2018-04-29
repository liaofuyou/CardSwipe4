package me.ajax.cardswipe4;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;

/**
 * Created by aj on 2018/4/24
 */

public class
AvatarLayoutManger extends RecyclerView.LayoutManager {


    private float slideFraction;
    private int currIndex;
    private int allOffset;
    private int viewWidth;
    private int viewHeight;
    private SparseArray<Rect> mAllItemFrames = new SparseArray<>();
    private SparseIntArray mAllItemScales = new SparseIntArray();
    private SparseBooleanArray mHasAttachedItems = new SparseBooleanArray();
    RecyclerView.Recycler recycler;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        if (getItemCount() == 0 || state.isPreLayout()) return;
        this.recycler = recycler;

        View view = recycler.getViewForPosition(0);
        addView(view);
        measureChildWithMargins(view, 0, 0);
        viewWidth = getDecoratedMeasuredWidth(view);
        viewHeight = getDecoratedMeasuredHeight(view);

        refreshFrames();

        //回收所有先
        detachAndScrapAttachedViews(recycler);

        //布局
        layoutItems(recycler);
    }

    public void scrollHorizontally(int index, float fraction) {
        if (getViewRealWidth() == 0 || getWidth() == 0) return;

        //头像的偏移
        allOffset = -(int) (index * getViewRealWidth() + getViewRealWidth() * fraction);
        //allOffset = Math.min(allOffset, 0);
        //allOffset = Math.max(allOffset, -getItemCount() * getViewRealWidth());

        currIndex = index;
        currIndex = Math.min(currIndex, getItemCount() - 1);

        slideFraction = fraction;

        refreshFrames();
        layoutItems(recycler);
    }

    private void refreshFrames() {

        int offsetLeft = viewWidth;
        int offsetTop = viewHeight;

        //初始化
        for (int i = 0; i < getItemCount(); i++) {

            Rect rect = mAllItemFrames.get(i);
            if (rect == null) rect = new Rect();
            rect.set(allOffset + offsetLeft, offsetTop, allOffset + offsetLeft + viewWidth, offsetTop + viewHeight);
            mAllItemFrames.put(i, rect);
            mHasAttachedItems.put(i, false);
            mAllItemScales.put(i, 100);

            offsetLeft += getViewRealWidth();
        }

        //位移
        Rect rect = mAllItemFrames.get(currIndex);
        rect.top += dp(50) * (1 - slideFraction);
        rect.bottom += dp(50) * (1 - slideFraction);

        //缩放
        mAllItemScales.put(currIndex, (int) (100 + 50 * (1 - slideFraction)));

        //位移
        rect = mAllItemFrames.get(currIndex + 1);
        if (rect == null) return;
        rect.top += dp(50) * slideFraction;
        rect.bottom += dp(50) * slideFraction;

        //缩放
        mAllItemScales.put(currIndex + 1, (int) (100 + 50 * slideFraction));

    }

    private int getViewRealWidth() {
        return viewWidth + viewWidth;//偏移一个viewWidth；
    }

    private void layoutItems(RecyclerView.Recycler recycler) {

        if (recycler == null) return;

        //回收或刷新位置

        for (int i = 0; i < getChildCount(); i++) {

            View view = getChildAt(i);
            int position = getPosition(view);

            if (position < currIndex || position > currIndex + 4) {
                removeAndRecycleView(view, recycler);
                mHasAttachedItems.put(position, false);
                l("回收" + position);
            } else {
                layoutItem(view, position);
                mHasAttachedItems.put(position, true);
                //l("移动" + position, currIndex);
            }
        }


        //添加新的
        for (int i = currIndex; i < currIndex + 5; i++) {

            if (i > getItemCount() - 1) continue;
            if (mHasAttachedItems.get(i)) continue;

            View view = recycler.getViewForPosition(i);

            addView(view);

            l("添加" + i, currIndex);

            layoutItem(view, i);
        }
    }

    private void layoutItem(View view, int i) {
        Rect rect = mAllItemFrames.get(i);
        float scale = mAllItemScales.get(i) / 100F;

        measureChildWithMargins(view, 0, 0);
        layoutDecoratedWithMargins(view, rect.left, rect.top, rect.right, rect.bottom);
        view.setScaleX(scale);
        view.setScaleY(scale);
    }

    private int dp(float dp) {
        return (int) dp * 3;
    }

    static void l(Object... list) {
        String text = "";
        for (Object o : list) {
            text += "   " + o.toString();
        }
        Log.e("######", text);
    }
}
