package example;

import java.math.BigDecimal;
import java.util.Date;

public class ExampleBigDecimal {

    public static void testZero() {
        BigDecimal[] foo = new BigDecimal[]{
            new java.math.BigDecimal("0"),
            new java.math.BigDecimal("0.0"),
            new java.math.BigDecimal("0.00"),
            new java.math.BigDecimal(0),
            new java.math.BigDecimal(0L),
            new java.math.BigDecimal(0f),
            new java.math.BigDecimal(0.0f),
            new java.math.BigDecimal(0.00f),
            new java.math.BigDecimal(0d),
            new java.math.BigDecimal(0.0d),
            new java.math.BigDecimal(0.00d),
            java.math.BigDecimal.valueOf(0),
            java.math.BigDecimal.valueOf(0L),
            java.math.BigDecimal.valueOf(0f),
            java.math.BigDecimal.valueOf(0.0f),
            java.math.BigDecimal.valueOf(0.00f),
            java.math.BigDecimal.valueOf(0d),
            java.math.BigDecimal.valueOf(0.0d),
            java.math.BigDecimal.valueOf(0.00d)};
    }

    public static void testOne() {
        BigDecimal[] foo = new BigDecimal[]{
            new java.math.BigDecimal("1"),
            new java.math.BigDecimal("1.0"),
            new java.math.BigDecimal("1.00"),
            new java.math.BigDecimal(1),
            new java.math.BigDecimal(1L),
            new java.math.BigDecimal(1f),
            new java.math.BigDecimal(1.0f),
            new java.math.BigDecimal(1.00f),
            new java.math.BigDecimal(1d),
            new java.math.BigDecimal(1.0d),
            new java.math.BigDecimal(1.00d),
            java.math.BigDecimal.valueOf(1),
            java.math.BigDecimal.valueOf(1L),
            java.math.BigDecimal.valueOf(1f),
            java.math.BigDecimal.valueOf(1.0f),
            java.math.BigDecimal.valueOf(1.00f),
            java.math.BigDecimal.valueOf(1d),
            java.math.BigDecimal.valueOf(1.0d),
            java.math.BigDecimal.valueOf(1.00d)};
    }
    public static void testTen() {
        BigDecimal[] foo = new BigDecimal[]{
            new java.math.BigDecimal("10"),
            new java.math.BigDecimal("10.0"),
            new java.math.BigDecimal("10.00"),
            new java.math.BigDecimal(10),
            new java.math.BigDecimal(10L),
            new java.math.BigDecimal(10f),
            new java.math.BigDecimal(10.0f),
            new java.math.BigDecimal(10.00f),
            new java.math.BigDecimal(10d),
            new java.math.BigDecimal(10.0d),
            new java.math.BigDecimal(10.00d),
            java.math.BigDecimal.valueOf(10),
            java.math.BigDecimal.valueOf(10L),
            java.math.BigDecimal.valueOf(10f),
            java.math.BigDecimal.valueOf(10.0f),
            java.math.BigDecimal.valueOf(10.00f),
            java.math.BigDecimal.valueOf(10d),
            java.math.BigDecimal.valueOf(10.0d),
            java.math.BigDecimal.valueOf(10.00d)};
    }
}
