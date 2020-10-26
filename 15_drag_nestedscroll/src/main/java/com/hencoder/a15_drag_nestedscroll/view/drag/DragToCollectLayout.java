package com.hencoder.a15_drag_nestedscroll.view.drag;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hencoder.a15_drag_nestedscroll.R;

import androidx.core.view.ViewCompat;

/**
 * OnDragListener 的使用场景
 *
 *
 */
public class DragToCollectLayout extends RelativeLayout {
    ImageView avatarView;
    ImageView logoView;
    LinearLayout collectorLayout;

    OnLongClickListener dragStarter = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            //跨进程数据的包装
            ClipData imageData = ClipData.newPlainText("name", v.getContentDescription());
//            imageData 是跨进程数据，比较重 ，只有在拖拽结束才能获取到数据；localState是当前进程数据相对轻量
            return ViewCompat.startDragAndDrop(v, imageData, new DragShadowBuilder(v), null, 0);
        }
    };
    OnDragListener dragListener = new CollectListener();

    public DragToCollectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //跨进程是通过 onDragEvent 接收到跨进程数据
    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        avatarView = findViewById(R.id.avatarView);
        logoView = findViewById(R.id.logoView);
        collectorLayout = findViewById(R.id.collectorLayout);

        avatarView.setOnLongClickListener(dragStarter);
        logoView.setOnLongClickListener(dragStarter);
        collectorLayout.setOnDragListener(dragListener);
    }

    class CollectListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP://只有在松手的时候才能获取到跨进程传递的数据，因为数据比较重
                    if (v instanceof LinearLayout) {
                        LinearLayout layout = (LinearLayout) v;
                        TextView textView = new TextView(getContext());
                        textView.setTextSize(16);
                        textView.setText(event.getClipData().getItemAt(0).getText());
                        layout.addView(textView);
                    }
                    break;
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
            }
            return true;
        }
    }

}
