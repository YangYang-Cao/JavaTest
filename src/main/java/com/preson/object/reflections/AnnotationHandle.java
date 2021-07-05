package com.preson.object.reflections;

@AnnotationTest
public class AnnotationHandle {

    @AnnotationTest
    public void name(){
        System.out.println("这是加上注解的方法");
    }

    public void test(){
        System.out.println("这是没有被加上注解的方法");
    }

}

