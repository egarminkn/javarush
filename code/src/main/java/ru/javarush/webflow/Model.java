package ru.javarush.webflow;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by eGarmin
 */
@Component
public class Model implements Serializable {

    private Sign.SignValue signValue;
    private double value;

    public Model() {

    }

    public Sign.SignValue getSignValue() {
        return signValue;
    }

    public void setSignValue(Sign.SignValue signValue) {
        this.signValue = signValue;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
