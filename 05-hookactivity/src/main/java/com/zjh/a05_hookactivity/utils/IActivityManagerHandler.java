package com.zjh.a05_hookactivity.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;


import com.zjh.a05_hookactivity.SubActivity;
import com.zjh.a05_hookactivity.TargetActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityManagerHandler implements InvocationHandler {

    private static final String TAG = "HookLearn";

    Object mBase;

    public IActivityManagerHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("startActivity".equals(method.getName())) {
            // 只拦截这个方法
            // 替换参数, 任你所为;甚至替换原始Activity启动别的Activity偷梁换柱
            // public final Activity startActivityNow(Activity parent, String id,
            // Intent intent, ActivityInfo activityInfo, IBinder token, Bundle state,
            // Activity.NonConfigurationInstances lastNonConfigurationInstances) {
            // 找到参数里面的第一个Intent 对象
            Intent raw;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                Log.d(TAG, "invoke: object= " + args[i]);
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            raw = (Intent) args[index];
            Log.i(TAG, "invoke: raw " + raw);

            Intent newIntent = new Intent();
            // 替身Activity的包名, 也就是我们自己的包名
            String stubPackage = "com.zjh.a05_hookactivity";

            // 这里我们把启动的Activity临时替换为 StubActivity
            ComponentName componentName = new ComponentName(stubPackage, SubActivity.class.getName());
            newIntent.setComponent(componentName);

            // 把我们原始要启动的TargetActivity先存起来
            newIntent.putExtra(HookHelper.EXTRA_TARGET_INTENT, raw);

            // 替换掉Intent, 达到欺骗AMS的目的
            args[index] = newIntent;

            return method.invoke(mBase, args);
        }
        return method.invoke(mBase, args);
    }
}
