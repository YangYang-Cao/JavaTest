package com.preson.object.java8.day01;

public class FilterEmployeeForAge implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee t) {
        return t.getAge() <= 35;
    }

}