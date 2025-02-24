package org.example;
import java.lang.reflect.Method;

public class Main2 {
    public static void main(String[] args) throws Exception{
        System.out.println( "main started");

        String neededClass = args[0];
        String neededMethod = args[1];

//        if(neededClass == neededMethod){
//            //сравнивать будет ссылки тк в джаве все ссылки
//        }
//
//        if(neededClass.equals(neededMethod)){
//            //вот теперь строки
//        }

        //копирование происходит только с помощью фции clone

        Class<?> c = Class.forName( "org.example.ClassToLoad");
        System.out.println("Loaded class: " + c.getName());

        Method[] methods = c.getDeclaredMethods();
        for(Method m: methods){
            System.out.println("Method: "+ m.getName());
        }

//        //Object o = c.newInstance();
//        Object o = c.getConstructor().newInstance();
//        Method m = c.getMethod("methodToCall");
//        m.invoke(o);//призвать Ctrl u


        Object o = c.getConstructor().newInstance();
        Command ca = (Command)o;
        ca.execute();
        //junit for test
    }
}