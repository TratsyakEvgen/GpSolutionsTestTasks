package com.gpsolutions.test.task109;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Integer> values = readValuesFromFile();

        String result = divide(values.get(0), values.get(1));

        Files.write(Path.of("OUTPUT.TXT"), result.getBytes());
    }

    private static List<Integer> readValuesFromFile() throws IOException {
        return Files.readAllLines(Path.of("INPUT.TXT"))
                .stream()
                .flatMap(s -> Stream.of(s.split("/")))
                .map(Integer::parseInt)
                .toList();
    }

    public static String divide(int a, int b) {
        StringBuilder result = new StringBuilder();
        result.append(a / b);

        int remainder = a % b;
        if (remainder == 0){
            return result.toString();
        }

        result.append(".");
        String part = calculateRemainder(remainder, b);

        return result.append(part).toString();
    }

    private static String calculateRemainder(int a, int b) {
        Map<Integer, Integer> periodPositionMap = new HashMap<>();
        StringBuilder part = new StringBuilder();
        int position = 0;

        while (a != 0) {
            if (periodPositionMap.containsKey(a)) {
                part.insert(periodPositionMap.get(a), "(").append(")");
                break;
            }

            periodPositionMap.put(a, position);
            a = a * 10;
            part.append(a / b);
            a = a % b;
            position++;
        }
        return part.toString();
    }
}
