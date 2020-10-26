package com.hencoder.a15_drag_nestedscroll.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DragListenerGridView extends ViewGroup {
    private static final int COLUMNS = 2;
    private static final int ROWS = 3;

    ViewConfiguration viewConfiguration;

    //拖拽事件监听器
    OnDragListener dragListener = new HenDragListener();

    //当前拖拽的view
    View draggedView;

    List<View> orderedChildren = new ArrayList<>();

    public DragListenerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewConfiguration = ViewConfiguration.get(context);
        setChildrenDrawingOrderEnabled(true);
    }

    /**
     * 布局文件加载完成后
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            orderedChildren.add(child); // 初始化位置
            child.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    draggedView = v;
                    v.startDrag(null, new DragShadowBuilder(v), v, 0);
                    return false;
                }
            });
            child.setOnDragListener(dragListener);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;

        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));

        setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            childLeft = index % 2 * childWidth;
            childTop = index / 2 * childHeight;

            //都放在同一位置
            child.layout(0, 0, childWidth, childHeight);
            //然后做移动
            child.setTranslationX(childLeft);
            child.setTranslationY(childTop);
        }
    }

    /***
     * 拖拽事件监听，响应拖拽的事件
     */
    private class HenDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getLocalState() == v) {//传递过来的拖拽view
                        v.setVisibility(INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if (event.getLocalState() != v) {//进入拖拽区域，事件回调
                        sort(v);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENDED://拖拽事件结束
                    v.setVisibility(VISIBLE);
                    break;
            }
            return true;
        }
    }

    /**
     *
     * @param targetView
     */
    private void sort(View targetView) {
        int draggedIndex = -1;
        int targetIndex = -1;
        for (int i = 0; i < getChildCount(); i++) {
            View child = orderedChildren.get(i);
            if (targetView == child) {
                targetIndex = i;
            } else if (draggedView == child) {
                draggedIndex = i;
            }
        }
        if (targetIndex < draggedIndex) {
            orderedChildren.remove(draggedIndex);
            orderedChildren.add(targetIndex, draggedView);
        } else if (targetIndex > draggedIndex) {
            orderedChildren.remove(draggedIndex);
            orderedChildren.add(targetIndex, draggedView);
        }
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < getChildCount(); index++) {
            View child = orderedChildren.get(index);
            childLeft = index % 2 * childWidth;
            childTop = index / 2 * childHeight;
            child.animate()
                    .translationX(childLeft)
                    .translationY(childTop)
                    .setDuration(150);
        }
    }
}
