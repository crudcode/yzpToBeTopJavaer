package com.yzp.toBeTopJavaer._1基础篇._1Java基础知识._5枚举;

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
