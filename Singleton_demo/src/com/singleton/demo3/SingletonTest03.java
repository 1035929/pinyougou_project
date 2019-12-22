package com.singleton.demo3;

public class SingletonTest03 {
    public static void main(String[] args) {

    }
}

class Singleton{

    private Singleton(){

    }

    private static class Singletons{
        private static Singleton instance;
    }

    public static Singleton getInstance(){
        return Singletons.instance;
    }
}