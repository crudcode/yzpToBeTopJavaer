package com.yzp.toBeTopJavaer.a_基础篇.枚举;

public enum Season {
    SPRING(1), SUMMER(2), AUTUMN(3), WINTER(4);

    private int code;

    private Season(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
