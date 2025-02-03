package com.gpsolutions.test.task193;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static int totalRows;
    private static int totalColumns;
    private static int totalRectangles;
    private static List<Rectangle> rectangles;
    private static Rectangle hiddenRectangle;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("INPUT.TXT"))) {

            initParameters(reader);
            initializeRectangles();
            updateRectangles(reader);
            String result = buildResult();

            Files.write(Path.of("OUTPUT.TXT"), result.getBytes());
        }
    }

    private static void initParameters(BufferedReader reader) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        totalRows = Integer.parseInt(tokenizer.nextToken());
        totalColumns = Integer.parseInt(tokenizer.nextToken());
        totalRectangles = Integer.parseInt(tokenizer.nextToken());
    }

    private static void initializeRectangles() {
        rectangles = new ArrayList<>();
        for (int i = 0; i <= totalRectangles; i++) {
            rectangles.add(new Rectangle(totalColumns + 1, 0, totalRows + 1, 0));
        }
        hiddenRectangle = rectangles.get(0);
    }

    private static String buildResult() {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= totalRectangles; i++) {
            Rectangle rectangle = rectangles.get(i);
            if (rectangle.left < totalColumns + 1) {
                result.append(rectangle);
            } else {
                result.append(hiddenRectangle);
            }
        }
        return result.toString();
    }

    private static void updateRectangles(BufferedReader reader) throws IOException {
        for (int row = 0; row < totalRows; row++) {
            StringTokenizer rowTokens = new StringTokenizer(reader.readLine());
            for (int column = 0; column < totalColumns; column++) {
                int rectangleIndex = Integer.parseInt(rowTokens.nextToken());
                if (rectangleIndex == 0) {
                    continue;
                }
                Rectangle rectangle = rectangles.get(rectangleIndex);
                updateRectangleBorders(rectangle, column, row);
                updateRectangleBorders(hiddenRectangle, column, row);
            }
        }
    }

    private static void updateRectangleBorders(Rectangle rectangle, int column, int row) {
        rectangle.left = Math.min(rectangle.left, column);
        rectangle.right = Math.max(rectangle.right, column);
        rectangle.up = Math.min(rectangle.up, totalRows - row - 1);
        rectangle.down = Math.max(rectangle.down, totalRows - row - 1);
    }

    private static class Rectangle {
        private int left;
        private int right;
        private int up;
        private int down;

        public Rectangle(int left, int right, int up, int down) {
            this.left = left;
            this.right = right;
            this.up = up;
            this.down = down;
        }

        @Override
        public String toString() {
            return String.format("%d %d %d %d%n", left, up, right + 1, down + 1);
        }
    }
}