package com.hencoder.a15_drag_nestedscroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_draghelper_gridview;
    Button bt_DragListenerGridView;
    Button bt_DragToCollectLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_scalable_image_view);
        bt_draghelper_gridview = findViewById(R.id.bt_draghelper_gridview);
        bt_DragListenerGridView = findViewById(R.id.bt_DragListenerGridView);
        bt_DragToCollectLayout = findViewById(R.id.bt_DragToCollectLayout);
        bt_draghelper_gridview.setOnClickListener(this);
        bt_DragListenerGridView.setOnClickListener(this);
        bt_DragToCollectLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == bt_draghelper_gridview){
            Intent intent = new Intent(this,DragHelperGridViewActivity.class);
            startActivity(intent);
        }else if (v == bt_DragListenerGridView){
            Intent intent = new Intent(this,DragListenerGridViewActivity.class);
            startActivity(intent);
        }else if (v == bt_DragToCollectLayout){
            Intent intent = new Intent(this,DragToCollectLayoutActivity.class);
            startActivity(intent);
        }

        RecyclerView recycleView = new RecyclerView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        RecyclerView.Adapter<MyViewHolder> adapter = new RecyclerView.Adapter<MyViewHolder>() {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };

        recycleView.setAdapter(adapter);

        /***
         * 如何判断RecyclerView是否滚动到底部或顶部?
         *
         * 以更常见的纵向来说，使用Recyclerview. canScrollVertically(1)，
         * 当返回值是false的时候，代表你的RecyclerView不能继续往下滚动啦，也就是说已经滚动到底部了。
         *
         * 同理，当Recyclerview. canScrollVertically(-1)返回false的时候代表RecyclerView不能继续网上滚动了，已经到顶部了。
         * 这个方法比通过计算当前item的位置以及总item数量的方法进行判断要好多了。
         */
        boolean canscroll = recycleView.canScrollVertically(1);

    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return 0;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}
