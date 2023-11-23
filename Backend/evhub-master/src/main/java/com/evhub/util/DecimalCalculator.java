package com.evhub.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class DecimalCalculator {

    private static final MathContext DECIMAL_64 = MathContext.DECIMAL64;

    public static BigDecimal getDefaultZero() {
        return new BigDecimal(0, DECIMAL_64);
    }

    public static BigDecimal add(BigDecimal... bigDecimal) {
        BigDecimal value = getDefaultZero();
        for (BigDecimal valueAdd : bigDecimal) {
            if (valueAdd != null) {
                value = value.add(valueAdd);
            }
        }
        return value;
    }
}
