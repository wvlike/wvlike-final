package com.wvlike.common.base.constants;

import java.util.function.DoubleBinaryOperator;

/**
 * package com.wvlike.testDmo.common.constant;
 *
 * @auther txw
 * @create 2020-09-22  22:19
 * @description：
 */
public enum Operation {

    PLUS("+", (x, y) -> x + y),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y),
    ;

    private String symbol;
    private DoubleBinaryOperator op;

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }

}
