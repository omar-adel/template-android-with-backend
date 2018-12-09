package com.basgeekball.awesomevalidation.model;

import android.support.annotation.Nullable;

import com.google.common.collect.Range;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class NumericRangeRule {

    private Range mRange;
     private String errMsg ="";

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


    public Range getmRange() {
        return mRange;
    }
    public NumericRangeRule(Range range, String errMsg) {
         mRange = range;
         this.errMsg=errMsg;
    }

    public NumericRangeRule(Range range   ) {
         mRange = range;

    }

    private static boolean isNumberFormat(String text) {
        return Pattern.compile("^-?(([1-9]\\d*(\\.\\d+)?)||(0\\.\\d+)||0)$").matcher(text).find();
    }

    public boolean isValid(String valueText) {
        if (!isNumberFormat(valueText)) return false;

        BigDecimal value;
        try {
            value = new BigDecimal(valueText);
        } catch (Exception e) {
            return false;
        }

        Boolean validityAsInteger = isInteger(value);
        if (validityAsInteger != null) return validityAsInteger;
        Boolean validityAsDecimal = isDecimal(value);
        if (validityAsDecimal != null) return validityAsDecimal;

        return false;
    }

    @Nullable
    private Boolean isInteger(BigDecimal value) {
        if (value.scale() == 0) {
            try {
                return mRange.contains(value.intValueExact());
            } catch (Exception e) {
            }
            try {
                return mRange.contains(value.longValueExact());
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Nullable
    private Boolean isDecimal(BigDecimal value) {
        try {
            return mRange.contains(value.floatValue());
        } catch (Exception e) {
        }
        try {
            return mRange.contains(value.doubleValue());
        } catch (Exception e) {
        }
        return null;
    }

    public Rule build(){
        return new Rule(this);
    }
}