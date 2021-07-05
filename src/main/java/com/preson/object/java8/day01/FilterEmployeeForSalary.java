package com.preson.object.java8.day01;

public class FilterEmployeeForSalary implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee t) {
        return t.getSalary() >= 5000;
    }

}