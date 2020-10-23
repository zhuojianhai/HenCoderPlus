package com.hencoder.plus.bindwrapper;

import android.os.IBinder;

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
