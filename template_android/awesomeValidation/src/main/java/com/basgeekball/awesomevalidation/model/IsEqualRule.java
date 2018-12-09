package com.basgeekball.awesomevalidation.model;

/**
 * Created by Net22 on 11/20/2017.
 */

public class IsEqualRule {

    public IsEqualRule( String errMsg) {
         this.errMsg = errMsg;
    }

    private String errMsg ="";

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Rule build(){
        return new Rule(this);
    }
}
