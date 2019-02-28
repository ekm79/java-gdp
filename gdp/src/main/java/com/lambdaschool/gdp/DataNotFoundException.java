package com.lambdaschool.gdp;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super("Data not found");
    }
}
