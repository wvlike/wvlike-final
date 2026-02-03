package com.wvlike.common.base.constants;

/**
 * package com.wvlike.testDmo.common.constant;
 *
 * @auther txw
 * @create 2020-09-22  22:24
 * @description：
 */
public enum OperationCopy {

    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    OperationCopy(String symbol) {
        this.symbol = symbol;
    }

    private String symbol;

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double apply(double x, double y);

}
