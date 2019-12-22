package com.it.demo;

public class SingletonTest01 {

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        System.out.println(instance==instance1);
        int a = instance.hashCode();
        int b = instance1.hashCode();
        System.out.println(a);
        System.out.println(b);
    }
}

    class Singleton{
        //1.构造器私有化，外部不能new
        private Singleton(){

        }

        //2.本内部创建对象实例
        private  static Singleton instance = new Singleton();

        //3.提供一个公有的静态方法，返回实例对象
        public static Singleton getInstance(){
            return instance;

        }
    }

