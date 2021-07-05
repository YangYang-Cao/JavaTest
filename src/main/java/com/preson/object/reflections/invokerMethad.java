package com.preson.object.reflections;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Set;

public class invokerMethad {

    @Test
    public void main() throws Exception {

        try {
            Class<?> aClass = Class.forName("com.baidu.test.AnnotationHandle");
            Object instance = aClass.getConstructor().newInstance();

            Method[] methods = aClass.getDeclaredMethods();
            if (aClass.isAnnotationPresent(AnnotationTest.class)) {

                for (Method method : methods) {
                    method.invoke(instance);

                }
            }


            for (Method method : methods) {
                System.out.println(method);
                if (method.isAnnotationPresent(AnnotationTest.class)) {
                    method.invoke(instance);
                }


            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void name() {

        Class<AnnotationTest> aClass = AnnotationTest.class;
        AnnotationTest annotation = aClass.getAnnotation(AnnotationTest.class);

    }

    @Test
    public void testMethod() {

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.baidu.test")
                .addScanners(new SubTypesScanner())
                .addScanners(new MethodParameterScanner())
                .addScanners(new MethodAnnotationsScanner())
                .addScanners(new ResourcesScanner())
                .addScanners(new FieldAnnotationsScanner()));

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(AnnotationTest.class);

        Set<Method> methodSet = reflections.getMethodsAnnotatedWith(AnnotationTest.class);

        for (Method method : methodSet) {
            System.out.println(method);
        }

        for (Class<?> aClass : annotated) {
            System.out.println(aClass);
        }

    }


    @Test
    public void names() {
        Reflections reflections = new Reflections("com.baidu.test");

        Set<Class<? extends AnnotationTest>> subTypes = reflections.getSubTypesOf(AnnotationTest.class);

        for (Class<? extends AnnotationTest> subType : subTypes) {
            System.out.println(subType);

        }

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(AnnotationTest.class);

        for (Class<?> aClass : annotated) {

            System.out.println(aClass);
        }

    }


    @Test
    public void named() {

        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages("com.baidu.test")
                .addScanners(new MethodAnnotationsScanner()));
//MethodAnnotationsScanner



        String name = AnnotationTest.class.getName();
        System.out.println(name);


        Set<Method> annotatedWith = reflections.getMethodsAnnotatedWith(AnnotationTest.class);
        for (Method method : annotatedWith) {
            System.out.println(method);
        }

        Set<Constructor> annotatedWith1 = reflections.getConstructorsAnnotatedWith(AnnotationTest.class);

        for (Constructor constructor : annotatedWith1) {
            System.out.println(constructor);
        }

    }

    @Test
    public void aVoid() {

        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.baidu.test")
                .addScanners(new SubTypesScanner())
                .addScanners(new MethodParameterScanner())
                .addScanners(new MethodAnnotationsScanner())
                .addScanners(new ResourcesScanner())
                .addScanners(new FieldAnnotationsScanner()));

        // 获取某个包下类型注解对应的类
        Set<Class<?>> typeClass = reflections.getTypesAnnotatedWith(AnnotationTest.class,true);
        for (Class<?> aClass : typeClass) {
            System.out.println(aClass);
        }


        // 获取资源文件
//        Set<String> properties = reflections.getResources(Pattern.compile(".*\\.properties"));
//        for (String property : properties) {
//            System.out.println(property);
//        }


    }

    @Test
    public void name1() {
        new ConfigurationBuilder().setInputsFilter(Input -> Input.endsWith(".class"));


        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.baidu.test")
                .addScanners(new SubTypesScanner())
                .addScanners(new MethodParameterScanner())
                .addScanners(new MethodAnnotationsScanner())
                .addScanners(new FieldAnnotationsScanner()));

        // 获取子类
        Set<Class<? extends A>> subTypes = reflections.getSubTypesOf(A.class);
        for (Class<? extends A> subType : subTypes) {
            System.out.println(subType);
        }

    }

    @Test
    public void testRef() {
        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.baidu.test")
                .addScanners(new SubTypesScanner())
                .addScanners(new MethodParameterScanner())
                .addScanners(new MethodAnnotationsScanner())
                .addScanners(new FieldAnnotationsScanner()));

        //获取注解对应的方法
        Set<Method> resources = reflections.getMethodsAnnotatedWith(AnnotationTest.class);
        for (Method resource : resources) {
            System.out.println(resource);
        }


    }

    @Test
    public void test001() {
        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.baidu.test").addScanners(new TypeAnnotationsScanner())
                .addScanners(new SubTypesScanner())
                .addScanners(new MethodParameterScanner())
                .addScanners(new MethodAnnotationsScanner())
                .addScanners(new FieldAnnotationsScanner()));


        //获取注解对应的字段
        Set<Field> with = reflections.getFieldsAnnotatedWith(AnnotationTest.class);
        for (Field field : with) {
            System.out.println(field);
        }

    }


    @Test
    public void nameTest(){
        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.baidu.test")
                .addScanners(new SubTypesScanner())
                .addScanners(new MethodParameterScanner())
                .addScanners(new MethodAnnotationsScanner())
                .addScanners(new FieldAnnotationsScanner()));


        //获取特定参数对应的方法
//        Set<Method> someMethods = reflections.getMethodsMatchParams(long.class, int.class);
//        for (Method someMethod : someMethods) {
//            System.out.println(someMethod);
//        }

//        Set<Method> voidMethods = reflections.getMethodsReturn(void.class);
//        for (Method voidMethod : voidMethods) {
//            System.out.println(voidMethod);
//        }

        Set<Method> pathParamMethods = reflections.getMethodsWithAnyParamAnnotated(AnnotationTest.class);
        for (Method paramMethod : pathParamMethods) {
            System.out.println(paramMethod);
        }

    }

    @Test
    public void namer() throws Exception {
        String s="com.badu.test.class";
        System.out.println(s.endsWith("class"));


        URL resource = Thread.currentThread().getContextClassLoader().getResource("com/baidu/test");
        System.out.println(resource);
        System.out.println(resource.getProtocol());
        String decode = URLDecoder.decode(resource.getFile(), "UTF-8");
        System.out.println(decode);

        Class<?> forName = Class.forName("com.baidu.test.b");
        boolean annotationPresent = forName.isAnnotationPresent(AnnotationTest.class);
        System.out.println(annotationPresent);
        Annotation[] declaredAnnotations = forName.getDeclaredAnnotations();

        for (Annotation annotation : declaredAnnotations) {

            System.out.println(annotation);
        }

    }
}
