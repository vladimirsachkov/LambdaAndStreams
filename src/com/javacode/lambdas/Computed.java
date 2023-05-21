package com.javacode.lambdas;

@FunctionalInterface
public interface Computed<T extends Number> {
    T Calculate(T a, T b);
}
