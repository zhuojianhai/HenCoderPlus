package com.hencoder.plus;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hencoder.HandlerLearnActivity;
import com.hencoder.plus.bean.User;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    IUserAIDLInterface iUserAIDLInterface;

    List<User> currentUser;


    boolean isConnected = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iUserAIDLInterface =   IUserAIDLInterface.Stub.asInterface(service);
            isConnected = true;
            Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this,"解绑成功",Toast.LENGTH_SHORT).show();
            isConnected = false;

        }
    };


    TextView tv;
    Button bind;
    Button unbind;

    Button addUser;
    Button getUser;
    Button sportview;
    Button bt_piechatview;
    Button arcSlideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RecyclerView rv = new RecyclerView(this);

        tv = findViewById(R.id.tv_user);
        bind = findViewById(R.id.bt_bindservice);
        unbind = findViewById(R.id.bt_unbindservice);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);

        addUser = findViewById(R.id.bt_adduser);
        getUser = findViewById(R.id.bt_getuser);
        sportview = findViewById(R.id.bt_sportview);
        arcSlideView = findViewById(R.id.bt_arcslidedview);
        bt_piechatview = findViewById(R.id.bt_piechatview);

        addUser.setOnClickListener(this);
        sportview.setOnClickListener(this);
        getUser.setOnClickListener(this);
        arcSlideView.setOnClickListener(this);
        bt_piechatview.setOnClickListener(this);



    }

    private void bindServiceMethod() {
        Intent serviceIntent = new Intent(this,MyUserService.class);
        bindService(serviceIntent,connection,BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View v) {
        if (v == bind){
            if (isConnected) return;
            bindServiceMethod();
        }else if(v == unbind){
            if (isConnected){
                unbindService(connection);
                isConnected = false;
            }


        }else if(v== addUser){
            Random random = new Random();
            int rs = random.nextInt(1000);
            User user = new User();
            user.setUserName("zhuojianhai"+rs);
            user.setAddress("江苏徐州"+rs);

            if (isConnected) {
                try {
                 boolean result =  iUserAIDLInterface.addUser(user);
                 if (result){
                     Toast.makeText(this,"添加用户成功",Toast.LENGTH_SHORT).show();
                 }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }else if(v==getUser){
            try {
                currentUser =  iUserAIDLInterface.getCurrentUser();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            showContent(currentUser);
        }else if(v==sportview){
            Intent intent = new Intent(this,SportsViewActivity.class);
            startActivity(intent);
        }else if(v ==arcSlideView){
            Intent intent = new Intent(this,ArcSlidingHelperActivity.class);
            startActivity(intent);
        }else if (v == bt_piechatview){
            Intent intent = new Intent(this, HandlerLearnActivity.class);
            startActivity(intent);
        }
    }

    private void showContent(List<User> currentUser) {
        if (currentUser!=null && currentUser.size()>0){
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<currentUser.size();i++){
                User u = currentUser.get(i);
                sb.append("name="+u.getUserName());
                sb.append("\n");
                sb.append("address="+u.getAddress());
                sb.append("\n\n");
            }

            tv.setText(sb.toString());
        }
    }

    private void showArrayMap(){
        Random random = new Random();
        int rs = random.nextInt(1000);
        User user = new User();
        user.setUserName("zhuojianhai"+rs);
        user.setAddress("江苏徐州"+rs);


        /***
         * HashMap 在android 系统上的数据优化集合类
         * ArrayMap、SpareArray
         * ArrayMap 的key 是任意值
         * SpareArray 的key 必须是int类型
         */
        ArrayMap<String,User> arrayMap = new ArrayMap<>();
        arrayMap.put("1",user);
        arrayMap.put("2",user);

        SparseArray<User> sparseArray = new SparseArray<>();
        sparseArray.put(1,user);
        sparseArray.put(2,user);
        sparseArray.put(3,user);
        sparseArray.put(4,user);
        sparseArray.put(5,user);


    }
    @Override
    protected void onDestroy() {
        if (isConnected){
            unbindService(connection);
        }

        super.onDestroy();
    }



    //    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
//
//        @NonNull
//        @Override
//        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return null;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return 0;
//        }
//    }

}
