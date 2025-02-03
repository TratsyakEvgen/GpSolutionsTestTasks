package com.gpsolutions.test.task548;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of("INPUT.TXT"));

        char[] firstDigits = inputLines.get(0).toCharArray();
        char[] secondDigits = inputLines.get(1).toCharArray();
        String smallestNumber = getSmallestNumber(firstDigits, secondDigits);

        Files.write(Path.of("OUTPUT.TXT"), smallestNumber.getBytes());
    }

    private static String getSmallestNumber(char[] firstDigits, char[] secondDigits) {
        StringBuilder result = new StringBuilder();
        int firstIndex = 0;
        int secondIndex = 0;

        while (firstIndex < firstDigits.length || secondIndex < secondDigits.length) {
            if (shouldTakeFromSecond(firstDigits, firstIndex, secondDigits, secondIndex)) {
                result.append(secondDigits[secondIndex++]);
            } else {
                result.append(firstDigits[firstIndex++]);
            }
        }

        return result.toString();
    }

    private static boolean shouldTakeFromSecond(char[] firstDigits, int firstIndex, char[] secondDigits, int secondIndex) {
        while (firstIndex < firstDigits.length && secondIndex < secondDigits.length) {
            if (firstDigits[firstIndex] != secondDigits[secondIndex]) {
                return firstDigits[firstIndex] > secondDigits[secondIndex];
            }
            firstIndex++;
            secondIndex++;
        }
        return firstIndex == firstDigits.length;
    }
}
