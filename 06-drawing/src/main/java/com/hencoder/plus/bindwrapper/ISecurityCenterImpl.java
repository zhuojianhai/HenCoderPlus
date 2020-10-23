package com.hencoder.plus.bindwrapper;

import android.os.RemoteException;

import com.hencoder.plus.ISecurityCenter;

public class ISecurityCenterImpl extends ISecurityCenter.Stub {

    @Override
    public String encrypt(String content) throws RemoteException {
        return null;
    }

    @Override
    public String decrypt(String content) throws RemoteException {
        return null;
    }
}
