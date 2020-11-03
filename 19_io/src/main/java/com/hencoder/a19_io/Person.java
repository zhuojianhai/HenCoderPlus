package com.hencoder.a19_io;

abstract public class Person  {
    private String name ="parent name";
    public Person(){
        super();
    }
    public Person(String name){
        this.name = name;
    }
    abstract  void showInfo(String name);
    public void showInf(){
        showInfo(this.name);
    }
}

