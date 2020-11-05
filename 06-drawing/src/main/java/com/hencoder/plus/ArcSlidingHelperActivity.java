package com.hencoder.plus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyr.arcslidinghelper.ArcSlidingHelper;

import java.util.ArrayList;
import java.util.List;

public class ArcSlidingHelperActivity extends AppCompatActivity {

    private ArcSlidingHelper mArcSlidingHelper;
    private TextView mView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_sliding_helper);
        mView = findViewById(R.id.tv_test);
        recyclerView = findViewById(R.id.rv_test);
//        mView.post(() -> {
//            //创建对象
//            mArcSlidingHelper = ArcSlidingHelper.create(mView,
//                    angle -> mView.setRotation(mView.getRotation() + angle));
//            //开启惯性滚动
//            mArcSlidingHelper.enableInertialSliding(true);
//            mArcSlidingHelper.setScrollAvailabilityRatio(0.1f);
//
//        });

        recyclerView.post(() -> {
            //创建对象
            mArcSlidingHelper = ArcSlidingHelper.create(recyclerView,
                    angle -> recyclerView.setRotation(recyclerView.getRotation() + angle));
            //开启惯性滚动
            mArcSlidingHelper.enableInertialSliding(true);
            mArcSlidingHelper.setScrollAvailabilityRatio(0.1f);

        });
        //拦截了点击事件
        getWindow().getDecorView().setOnTouchListener((v,event)->{
            //处理滑动事件
            mArcSlidingHelper.handleMovement(event);
            return true;

        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MyRecyclerviewAdapter());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mArcSlidingHelper.release();
    }

    class  MyRecyclerviewAdapter extends RecyclerView.Adapter<MyViewHolder>{

        List<String> data = new ArrayList<>();
        {
            for (int i =0;i<120;i++){
                data.add("我是王大锤子 "+i);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =     LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_layout,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.textView.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item_name);
        }
    }
}