package com.gpsolutions.task346;

import java.io.*;
import java.util.Arrays;

public class Main {
    private static boolean result;
    private static int resultFirstValue;
    private static int resultSecondValue;

    public static void main(String[] args) throws IOException {
        String[] values = readValuesFromFile();
        findNumbers(values[0].toCharArray(), values[1].toCharArray(), Integer.parseInt(values[2]));
        writeAnswerInFile();
    }

    private static String[] readValuesFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("INPUT.TXT"))) {
            return reader.readLine().split(" ");
        }
    }

    private static void writeAnswerInFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("OUTPUT.TXT"))) {
            String output = result ? "YES\n" + resultFirstValue + " " + resultSecondValue : "NO";
            writer.write(output);
        }
    }

    private static void findNumbers(char[] firstArrayDigits, char[] secondArrayDigits, int target) {
        Arrays.sort(firstArrayDigits);
        Arrays.sort(secondArrayDigits);
        int firstValue = -1;

        while (!result && firstArrayDigits != null && firstValue <= target) {
            firstValue = parseCharArrayToInt(firstArrayDigits);
            int different = target - firstValue;
            char[] digitsDifferent = String.valueOf(different).toCharArray();
            Arrays.sort(digitsDifferent);
            digitsDifferent = addLeadingZeros(digitsDifferent, secondArrayDigits.length);

            if (Arrays.equals(secondArrayDigits, digitsDifferent)) {
                result = true;
                resultFirstValue = firstValue;
                resultSecondValue = different;
            }
            firstArrayDigits = generateNextBiggerPermutation(firstArrayDigits);
        }
    }

    public static char[] addLeadingZeros(char[] digits, int totalLength) {
        int originalLength = digits.length;

        if (originalLength >= totalLength) {
            return digits;
        }

        String result = "0".repeat(totalLength - originalLength) + String.valueOf(digits);
        return result.toCharArray();
    }

    private static int parseCharArrayToInt(char[] digits) {
        return Integer.parseInt(new String(digits));
    }

    private static char[] generateNextBiggerPermutation(char[] digits) {
        int length = digits.length;
        int i = length - 1;

        while (i > 0 && digits[i - 1] >= digits[i]) {
            i--;
        }

        if (i == 0) {
            return null;
        }

        int j = length - 1;
        while (digits[j] <= digits[i - 1]) {
            j--;
        }

        swap(digits, i - 1, j);
        reverse(digits, i, length - 1);
        return digits;
    }

    private static void reverse(char[] digits, int start, int end) {
        while (start < end) {
            swap(digits, start++, end--);
        }
    }

    private static void swap(char[] digits, int i, int j) {
        char temp = digits[i];
        digits[i] = digits[j];
        digits[j] = temp;
    }
}