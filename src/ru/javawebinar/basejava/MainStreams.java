package ru.javawebinar.basejava;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreams {

    public static void main(String[] args) {
        System.out.println("minValue() method");
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(minValue(new int[]{1, 2, 4, 3, 0, 0, 3, 2, 3}));
        System.out.println(minValue(new int[]{0, 0, 0}));
        System.out.println(minValue(new int[]{0, 1, 0}));
        System.out.println();

        System.out.println("oddOrEven() method");
        System.out.println(oddOrEven(Arrays.asList(1, 2, 0, 3, 3, 2, 3))); //1, 3, 3, 3
        System.out.println(oddOrEven(Arrays.asList(0, 0, 0))); //[]
        System.out.println(oddOrEven(Arrays.asList(9, 8, 0))); //8, 0
        System.out.println();

        System.out.println("oddOrEvenOptional() method");
        System.out.println(oddOrEvenOptional(Arrays.asList(1, 2, 0, 3, 3, 2, 3))); //1, 3, 3, 3
        System.out.println(oddOrEvenOptional(Arrays.asList(0, 0, 0))); //[]
        System.out.println(oddOrEvenOptional(Arrays.asList(9, 8, 0, 1))); //9, 1
    }

    private static int minValue(int[] values) {
        return IntStream.of(values).distinct().sorted().reduce(0, (a, b) -> (10 * a + b));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Objects.requireNonNull(integers, "Add some Integers to list!");
        int rem = integers.stream().mapToInt(Integer::intValue).sum() % 2;
        return integers.stream().filter(n -> n % 2 != rem).collect(Collectors.toList());
    }

    private static List<Integer> oddOrEvenOptional(List<Integer> integers) {
        Objects.requireNonNull(integers, "Add some Integers to list!");
        Map<Boolean, List<Integer>> map = integers.stream().
                collect(Collectors.partitioningBy(x -> x % 2 == 0, Collectors.toList()));
        return map.get(map.get((false)).size() % 2 != 0);
    }
}
