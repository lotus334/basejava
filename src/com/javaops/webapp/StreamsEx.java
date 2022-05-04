package com.javaops.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsEx {
    public static void main(String[] args) {
        int a = minValue(new int[]{1, 2, 3, 3, 2, 3});
        int b = minValue(new int[]{9,8});

        System.out.println(a);
        System.out.println(b);

        List<Integer> list1 = List.of(1, 2, 3);
        List<Integer> list2 = List.of(1, 2, 3, 4, 5);

        System.out.println(oddOrEven(list1));
        System.out.println(oddOrEven(list2));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a, b) -> 10 * a + b);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        Stream<Integer> stream = integers.stream();
        boolean isEven = stream.reduce(0, Integer::sum) % 2 == 0 ;
        return stream.filter(integer -> isEven == (integer % 2 != 0)).collect(Collectors.toList());
    }
}
