package com.hencoder.a28_lib_reflection;

import android.app.Activity;

import java.lang.reflect.Field;

public class Binding {
    public static void bind(Activity activity) {
        for (Field field : activity.getClass().getDeclaredFields()) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bindView(Activity activity){
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field :fields){
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView!=null){
                field.setAccessible(true);
                try {
                    field.set(activity,bindView.value());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
