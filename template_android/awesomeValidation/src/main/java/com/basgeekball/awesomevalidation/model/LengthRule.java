package com.basgeekball.awesomevalidation.model;

/**
 * Created by Net22 on 11/19/2017.
 */

public class LengthRule {

    private int minLength=0;
    private int maxLength=1000;
    private String errMsg ="";

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


    public LengthRule(int minLength, int maxLength, String errMsg) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.errMsg = errMsg;
    }


    public LengthRule(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;

    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public Rule build(){
        return new Rule(this);
    }
}
