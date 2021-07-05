package com.preson.object.java8.day01;

@FunctionalInterface
public interface MyPredicate<T> {

    public boolean test(T t);

}