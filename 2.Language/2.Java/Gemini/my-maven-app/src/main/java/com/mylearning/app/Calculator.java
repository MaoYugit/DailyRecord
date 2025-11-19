package com.mylearning.app;

public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            // 对于除零的异常情况，我们抛出一个异常
            throw new IllegalArgumentException("Divisor cannot be zero");
        }
        return a / b;
    }
}