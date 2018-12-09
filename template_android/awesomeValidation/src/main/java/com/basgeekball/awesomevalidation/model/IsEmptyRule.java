package com.basgeekball.awesomevalidation.model;

/**
 * Created by Net22 on 11/19/2017.
 */

public class IsEmptyRule {
     private String errMsg ="";

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isEmpty(String valueText) {
        if (isEmpty(valueText)) return true;
        else return false ;
    }
    public IsEmptyRule(String errMsg) {
         this.errMsg = errMsg;
    }

    public Rule build(){
        return new Rule(this);
    }

}
