package com.hencoder.plus;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
        addUser.setOnClickListener(this);
        getUser.setOnClickListener(this);


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
