package com.javacode.lambdas;

public class CalculatorMain {
    public static void main(String[] args) {
        int a = 5;
        double b = 4.23;
        System.out.println(a * b);

        process(a, b, (x, y) -> a*b);
    }

    public static <T extends Number> void process (T a, T b, Computed<T> computed){
        System.out.println(computed.Calculate(a,b));
    }
}
