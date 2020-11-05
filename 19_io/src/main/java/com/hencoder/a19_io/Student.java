package com.hencoder.a19_io;

public class Student extends Person {
    private String name;
    public Student(){

    }
    public Student(String name){
        this.name = name;
    }
    @Override
    void showInfo(String name) {

        System.out.println("this name is "+this.name);
    }

    public static void main(String[] args) {
        new Student("zhuojianhai").showInfo("");

        final ThreadLocal<Student> threadLocal = new ThreadLocal<Student>(){
            @Override
            protected Student initialValue() {
                return new Student("jack");
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
               threadLocal.set(new Student("rose"));

               Student s = threadLocal.get();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(new Student("rose-son"));
            }
        });

    }
}
