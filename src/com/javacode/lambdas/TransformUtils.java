package com.javacode.lambdas;

public class TransformUtils<T> {

    T transform(T t, Transformable<T> function){
        return function.transform(t);
    };

    static String exclaim(String string) {
        return string.toUpperCase()+"!!!!";
    }
}
