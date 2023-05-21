package com.javacode.lambdas;

import java.util.*;

@FunctionalInterface
interface ElementProcessor<T extends Number> {
    double process(T element);
}

@FunctionalInterface
interface ExecutiveFunction{
    void process();
}

public class LambdaExample {

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        intList.add(4);
        intList.add(5);
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(6.4);
        doubleList.add(8.6);
        doubleList.add(1.23);
        doubleList.add(4.13);
        doubleList.add(12.2);

        processElements(intList, (x)->Math.sin(x.doubleValue()));
        processElements(doubleList, (x)->Math.sin(x.doubleValue()));

        TimeUntil.measure(()->Arrays.sort(createRandomArray()));

        String s = "Hello ";
        Double d = 0.123;

        TransformUtils<Double> doubleUtils = new TransformUtils<>();
        System.out.println(doubleUtils.transform(d, x->Math.sin(x)));

        TransformUtils<String> stringUtils = new TransformUtils<>();
        System.out.println(stringUtils.transform(s, TransformUtils::exclaim));

        String suffix = "Alex";
        System.out.println(stringUtils.transform(suffix, s::concat));

        System.out.println(stringUtils.transform(s, String::toUpperCase));
        System.out.println(stringUtils.transform(s, String::new));

    }

    private static <T extends Number> void processElements(List<T> intList, ElementProcessor function) {
        List<Double> doubleList = new ArrayList<>();
        for (Number i : intList) {
            doubleList.add(function.process(i));
        }
        System.out.println(doubleList);
    }

    private static double multiply(int x, int y){
        return x * y / 10.0;
    }

    private static int[] createRandomArray() {
        int[] myArray = new int[1000000];
        Random r = new Random();
        for (int i = 0; i < myArray.length; i++){
            myArray[i] = r.nextInt(myArray.length);
        }
        return myArray;
    }

    public static class TimeUntil{

        private static void measure(ExecutiveFunction function) {
            long start = System.currentTimeMillis();
            function.process();
            long end = System.currentTimeMillis();
            System.out.println("Time spent "+(end-start));
        }
    }
}
