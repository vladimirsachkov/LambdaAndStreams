package com.javacode.lambdas.model;

public class Circle implements Shape, AnotherShape {
    public Circle() {System.out.println("Creating circle");}

    @Override
    public double calcSquare() {
        return 0;
    }

    public double calcSomething() {
        return Shape.super.calcSomething();
    }
}
