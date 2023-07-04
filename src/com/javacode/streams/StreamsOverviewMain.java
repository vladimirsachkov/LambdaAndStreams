package com.javacode.streams;

import com.javacode.lambdas.model.Employee;
import com.javacode.lambdas.model.EmployeeForStream;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

//        testStreamFormList();

        testStreamFormFile();
    }

    private static void testStreamFormList() {
//        employeeList.stream()
//                .filter(e -> e.getSalary() > 60000)
//                .filter(e -> e.getId() < 10)
//                .collect(Collectors.toList())
//                .forEach(System.out::println);

        Integer[] ids = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,18,19,20};

        Stream.of(ids)
                .map(StreamsOverviewMain::findBuId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    private static void testStreamFormFile() throws IOException {
        Files.lines(Paths.get("words.txt"))
                .filter(e -> e.length() > 4)
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    private static EmployeeForStream findBuId(int id) {
        if (employeeMap == null) {
            employeeMap = employeeList.stream().distinct().collect(Collectors.toMap(EmployeeForStream::getId, e-> e));
        }
        return employeeMap.get(id);
    }
}
