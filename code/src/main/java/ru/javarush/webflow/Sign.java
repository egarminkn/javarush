package ru.javarush.webflow;

import org.springframework.stereotype.Component;

/**
 * Created by eGarmin
 */
@Component
public class Sign {

    public static enum SignValue {
        ZERO,
        NEGATIVE,
        POSITIVE;
    }

    public SignValue getSignValue(double x) {
        if (x < 0) {
            return SignValue.NEGATIVE;
        }
        if (x > 0) {
            return SignValue.POSITIVE;
        }
        return SignValue.ZERO;
    }

}
