package com.it.demo2;

public class SingletonTest03 {

   private SingletonTest03(){

   }

   private static class SingletonManger{
       private final static SingletonTest03 instance=new SingletonTest03();
   }

   public static SingletonTest03 getInstance(){
       return SingletonManger.instance;
   }
}

