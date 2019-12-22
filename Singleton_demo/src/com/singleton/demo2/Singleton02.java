package com.singleton.demo2;

public class Singleton02 {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        System.out.println(instance==instance1);
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());
    }
}

class Singleton{

    private static volatile Singleton instance;

    private Singleton(){

    }



    public static Singleton getInstance(){
        if (instance==null){
           synchronized (Singleton.class){
               if (instance==null){
                   instance=new Singleton();
               }
           }
        }
        return instance;
    }
}
