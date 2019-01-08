package com.neo.test.snow;

import java.util.Arrays;
import java.util.List;

/**
 * @author Huangcz
 * @date 2018-10-22 09:50
 * @desc
 */
public class Main {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);

        numbers.stream()
                .filter(i -> i % 2 != 0)
                .distinct()
                .forEach(System.out::println);

        System.out.println("22222222222222222222");

    }

}
