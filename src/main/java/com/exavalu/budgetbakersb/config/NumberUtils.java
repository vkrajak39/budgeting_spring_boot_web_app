package com.exavalu.budgetbakersb.config;
import java.text.DecimalFormat;

public final class NumberUtils {
    NumberUtils() {}

    public static String formatDecimal(Double value, String pattern) {
        if (value == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }
}
