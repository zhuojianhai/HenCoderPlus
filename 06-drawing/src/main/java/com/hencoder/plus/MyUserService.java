package com.hencoder.plus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.hencoder.plus.bean.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyUserService extends Service {
    private static final String TAG = "MyUserService";

    //同步list容器
    private CopyOnWriteArrayList<User> userCopyOnWriteArrayList = new CopyOnWriteArrayList<>();

    RemoteCallbackList remoteCallbackList;


    public MyUserService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyUserServiceBinder();
    }

    /**
     * 返回给顾客端的Binder代理对象
     */
   class MyUserServiceBinder extends IUserAIDLInterface.Stub{

       @Override
       public boolean addUser(User user) throws RemoteException {
           userCopyOnWriteArrayList.add(user);
           return false;
       }

       @Override
       public List<User> getCurrentUser() throws RemoteException {
           return userCopyOnWriteArrayList;
       }
   }
}
