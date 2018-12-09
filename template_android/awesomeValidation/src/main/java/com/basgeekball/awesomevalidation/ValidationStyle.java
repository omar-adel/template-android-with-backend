package com.basgeekball.awesomevalidation;

public enum ValidationStyle {

    BASIC(0);

    private int mValue;

    ValidationStyle(int value) {
        mValue = value;
    }

    public int value() {
        return mValue;
    }

    public static ValidationStyle fromValue(int value) {
        switch (value) {
            case 0:
                return ValidationStyle.BASIC;
            default:
                throw new IllegalArgumentException("Unknown ValidationStyle value.");
        }
    }

}