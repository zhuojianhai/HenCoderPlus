package com.hencoder.a10_layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams  params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);



        RecyclerView recyclerView = new RecyclerView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        RecyclerView.Adapter<MyAdapterViewHolder> adapterAdapter = new RecyclerView.Adapter<MyAdapterViewHolder>() {
            @NonNull
            @Override
            public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };
        recyclerView.setAdapter(adapterAdapter);

        linearLayout.addView(recyclerView);

    }

    private class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
