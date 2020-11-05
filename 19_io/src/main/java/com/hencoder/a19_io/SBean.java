package com.hencoder.a19_io;

import com.google.gson.Gson;

public class SBean {
    private String Name;
    private int Age;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }


    @Override
    public String toString() {
        return "SBean{" +
                "Name='" + Name + '\'' +
                ", Age=" + Age +
                '}';
    }

    public static void main(String[] args) {
        SBean sBean = new SBean();
        sBean.setName("zhuojianhai");
        sBean.setAge(30);
        Gson gson = new Gson();

        String string = gson.toJson(sBean);
        System.out.println(string);

        SBean ss =  gson.fromJson(string,SBean.class);
        System.out.println(ss.toString());

       final ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("main String");


        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("child1 string");
                System.out.println(Thread.currentThread() +threadLocal.get());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("child2 string");

                System.out.println(Thread.currentThread() +threadLocal.get());
            }
        }).start();

    }
}
