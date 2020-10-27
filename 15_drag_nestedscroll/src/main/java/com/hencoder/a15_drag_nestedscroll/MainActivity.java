package com.hencoder.a15_drag_nestedscroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    }
}
