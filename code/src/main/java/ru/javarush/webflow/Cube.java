package ru.javarush.webflow;

import org.springframework.stereotype.Component;

/**
 * Created by eGarmin
 */
@Component
public class Cube {

    public int calcCube(int x) {
        return x * x * x;
    }

}
