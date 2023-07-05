package com.javacode.streams;

import com.javacode.lambdas.model.EmployeeForStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsOverviewMain {

    private static List<EmployeeForStream> employeeList = new ArrayList<>();
    private static List<EmployeeForStream> secondList = new ArrayList<>();
    private static Map<Integer, EmployeeForStream> employeeMap = null;

    public static void main(String[] args) throws IOException {
        employeeList.add(new EmployeeForStream(1, "Alex", "Black", 50000, "IT"));
        employeeList.add(new EmployeeForStream(2, "John", "Green", 75000, "IT"));
        employeeList.add(new EmployeeForStream(6, "Sam", "Brown", 80000, "IT"));
        employeeList.add(new EmployeeForStream(9, "Tony", "Grey", 90000, "IT"));
        employeeList.add(new EmployeeForStream(10, "Mike", "Yellow", 60000, "IT"));
        employeeList.add(new EmployeeForStream(11, "Victoria", "Pink", 75000, "IT"));
        employeeList.add(new EmployeeForStream(16, "Sean", "Magenta", 80000, "Finance"));
        employeeList.add(new EmployeeForStream(19, "Kate", "Black", 88000, "Finance"));
        employeeList.add(new EmployeeForStream(9, "Tony", "Grey", 90000, "Finance"));
        employeeList.add(new EmployeeForStream(10, "Mike", "Yellow", 60000, "IT"));
        employeeList.add(new EmployeeForStream(11, "Victoria", "Pink", 75000, "Finance"));

        secondList.add(new EmployeeForStream(1, "Alex", "Black", 50000, "IT"));
        secondList.add(new EmployeeForStream(2, "John", "Green", 75000, "IT"));
        secondList.add(new EmployeeForStream(6, "Sam", "Brown", 80000, "IT"));
        secondList.add(new EmployeeForStream(9, "Tony", "Grey", 90000, "IT"));
        secondList.add(new EmployeeForStream(10, "Mike", "Yellow", 60000, "IT"));
        secondList.add(new EmployeeForStream(11, "Victoria", "Pink", 75000, "IT"));
        secondList.add(new EmployeeForStream(16, "Sean", "Magenta", 80000, "Finance"));
        secondList.add(new EmployeeForStream(19, "Kate", "Black", 88000, "Finance"));
        secondList.add(new EmployeeForStream(9, "Tony", "Grey", 90000, "Finance"));
        secondList.add(new EmployeeForStream(10, "Mike", "Yellow", 60000, "IT"));
        secondList.add(new EmployeeForStream(11, "Victoria", "Pink", 75000, "Finance"));

//        testSortAndReduce();
//        testStreamFormList();
//
//        testStreamFormFile();
//
//        partitionByIncome();

//        groupByCriterion(EmployeeForStream::getDepartment);

//        testStreamGenerator(10);
//        testStreamIterator(10);

//        testParallelStream();

        Supplier<Integer> supplier = new Supplier<Integer>() {
            private int previous = 0;
            private int current = 1;
            @Override
            public Integer get() {
                int next = previous + current;
                previous = current;
                current = next;
                return current;
            }
        };

        testStreamGeneratorv2(10, supplier);
    }

    private static void testParallelStream() throws IOException {
        employeeList
                .parallelStream()
                .map(EmployeeForStream::getId)
                .sorted()
                .collect(Collectors.toList())
                .forEach(System.out::println);

        Files.lines(Paths.get("words.txt"))
                .parallel()
                .sorted();
    }

    private static void testStreamIterator(int limit) {
        Stream.iterate(1, e -> e * 3).limit(limit).forEach(System.out::println);
    }

    private static void testStreamGenerator(int limit) {
        Stream.generate(Math::random).limit(limit).forEach(System.out::println);
    }

    private static <T> void testStreamGeneratorv2(int limit, Supplier<T> supplier) {
        Stream.generate(supplier)
                .parallel()
                .limit(limit).forEach(System.out::println);
    }

    private static <R> void groupByCriterion(Function<EmployeeForStream, R> function) {
        Map<R, List<EmployeeForStream>> collectEmployees = employeeList.stream().collect(Collectors.groupingBy(function));
        collectEmployees.keySet().stream().forEach(e -> System.out.println(e + "\n" + collectEmployees.get(e)));
    }

    private static void partitionByIncome() {
        Map<Boolean, List<EmployeeForStream>> collectEmployees = employeeList.stream().collect(Collectors.partitioningBy(e -> e.getSalary() > 70000));
        System.out.println("Poor employees");
        System.out.println(collectEmployees.get(false));

        System.out.println("Reach employees");
        System.out.println(collectEmployees.get(true));
    }

    private static void testSortAndReduce() {
        EmployeeForStream employeeForStream = employeeList.stream()
                .max((e1, e2) -> e1.getId() - e2.getId()).get();

        EmployeeForStream employeeForStream1 = employeeList.stream()
                .min(Comparator.comparingInt(EmployeeForStream::getSalary)).get();

        System.out.println(employeeForStream + "\n");
        System.out.println(employeeForStream1 + "\n");

        //1 способ
        employeeList.stream()
                .sorted(Comparator.comparingInt(EmployeeForStream::getSalary))
                .collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("\n");

        //2 способ
        employeeList.stream()
                .sorted((s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName()))
                .collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("\n");

        EmployeeForStream identiti = new EmployeeForStream(0, "", "", 0, "");
        EmployeeForStream reducedEmployee = employeeList.stream()
                .reduce(identiti, (e1, e2) -> {
                    e1.setId(e1.getId() + e2.getId());
                    e1.setSalary(e1.getSalary() + e2.getSalary());
                    return e1;
                });

        System.out.println(reducedEmployee);
    }

    private static void testStreamFormList() {
        employeeList.stream()
                .filter(e -> e.getSalary() > 60000)
                .filter(e -> e.getId() < 10)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        Integer[] ids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 18, 19, 20};

//        Stream.of(ids)
//                .map(StreamsOverviewMain::findById)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList())
//                .forEach(System.out::println);
//
//        OptionalInt first = Stream.of(ids)
//                .map(StreamsOverviewMain::findById)
//                .filter(Objects::nonNull)
//                .mapToInt(EmployeeForStream::getSalary)
//                .findFirst();
//
//        System.out.println(first);

        Random r = new Random();

        Integer integer = Stream.of(ids)
                .filter(i -> i % 2 == 0)
                .filter(i -> i % 3 == 0)
                .filter(i -> i % 5 == 0)
                .findFirst()
                .orElseGet(() -> r.nextInt());

        System.out.println(integer);


        int sum = Stream.of(ids)
                .map(StreamsOverviewMain::findById)
                .filter(Objects::nonNull)
                .mapToInt(EmployeeForStream::getSalary)
                .sum();

        OptionalDouble average = Stream.of(ids)
                .map(StreamsOverviewMain::findById)
                .filter(Objects::nonNull)
                .mapToInt(EmployeeForStream::getSalary)
                .average();

        OptionalInt max = Stream.of(ids)
                .map(StreamsOverviewMain::findById)
                .filter(Objects::nonNull)
                .mapToInt(EmployeeForStream::getSalary)
                .max();

        System.out.println("sum: " + sum);
        System.out.println("average: " + average);
        System.out.println("max: " + max);

        List<List<EmployeeForStream>> departments = new ArrayList<>();
        departments.add(employeeList);
        departments.add(secondList);

        departments.stream().flatMap(l -> l.stream().map(e -> e.getFirstName())).forEach(System.out::println);

//        int sum1 = 0;
//        Consumer<Integer> c = e -> e = e * 2;
//        Stream.of(ids)
//                .forEach(c);

    }

    private static void testStreamFormFile() throws IOException {
        Files.lines(Paths.get("words.txt"))
                .filter(e -> e.length() > 4)
                .map(String::toUpperCase)
//                .distinct()
//                .sorted()
                .collect(Collectors.toCollection(TreeSet::new))
                .forEach(System.out::println);
    }

    private static EmployeeForStream findById(int id) {
        if (employeeMap == null) {
            employeeMap = employeeList.stream().distinct().collect(Collectors.toMap(EmployeeForStream::getId, e -> e));
        }
        return employeeMap.get(id);
    }
}
