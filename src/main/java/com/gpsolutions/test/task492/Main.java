package com.gpsolutions.test.task492;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of("INPUT.TXT"));

        Values values = parseValues(inputLines);
        boolean isAchievableDistance = isAchievableDistance(values);

        Files.write(Path.of("OUTPUT.TXT"), isAchievableDistance ? "YES".getBytes() : "NO".getBytes());
    }

    private static boolean isAchievableDistance(Values values) {
        BigDecimal distanceForTarget = getDistanceForTarget(values.x0, values.Vx, values.t, values.y0, values.Vy);
        BigDecimal maxDistance = new BigDecimal(values.V.multiply(values.t));
        BigDecimal different = distanceForTarget.subtract(new BigDecimal(values.d)).abs();
        return different.compareTo(maxDistance) <= 0;
    }

    private static BigDecimal getDistanceForTarget(BigInteger x0, BigInteger Vx, BigInteger t, BigInteger y0, BigInteger Vy) {
        BigInteger temp = (x0.add(Vx.multiply(t))).pow(2).add((y0.add(Vy.multiply(t))).pow(2));
        return new BigDecimal(temp).sqrt(new MathContext(50, RoundingMode.HALF_UP));
    }

    private static Values parseValues(List<String> inputLines) {
        List<BigInteger> listValues = inputLines.stream()
                .flatMap(s -> Stream.of(s.split(" ")))
                .map(BigInteger::new)
                .toList();

        return new Values(listValues);
    }

    private static class Values {
        private final BigInteger x0;
        private final BigInteger y0;
        private final BigInteger Vx;
        private final BigInteger Vy;
        private final BigInteger V;
        private final BigInteger t;
        private final BigInteger d;

        public Values(List<BigInteger> listValues) {
            this.x0 = listValues.get(0);
            this.y0 = listValues.get(1);
            this.Vx = listValues.get(2);
            this.Vy = listValues.get(3);
            this.V = listValues.get(4);
            this.t = listValues.get(5);
            this.d = listValues.get(6);
        }
    }
}
