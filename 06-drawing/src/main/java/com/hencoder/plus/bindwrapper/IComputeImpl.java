package com.hencoder.plus.bindwrapper;

import android.os.RemoteException;

import com.hencoder.plus.ICumpute;

public class IComputeImpl extends ICumpute.Stub{
    @Override
    public int add(int anInt, int bInt) throws RemoteException {
        return 0;
    }
}
