package ru.javarush.webflow;

import org.springframework.stereotype.Component;

/**
 * Created by eGarmin
 */
@Component
public class CubeRoot {

    public double calcCubeRoot(int x) {
        return Math.pow(x, 1./3);
    }

}
