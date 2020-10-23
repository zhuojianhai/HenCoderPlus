package com.hencoder.plus.bindwrapper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BinderPoolService extends Service {
    public BinderPoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
