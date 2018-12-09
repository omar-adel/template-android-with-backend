package com.basgeekball.awesomevalidation.model;

import java.util.regex.Pattern;

/**
 * Created by Net22 on 11/19/2017.
 */

public class PatternRule {
    private Pattern mPattern;
     private String errMsg ="";

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public PatternRule(Pattern mPattern,   String errMsg) {
        this.mPattern = mPattern;
         this.errMsg = errMsg;
    }

    public PatternRule(String ePattern,   String errMsg) {
        this.mPattern   = java.util.regex.Pattern.compile(ePattern);
         this.errMsg = errMsg;
    }

    public PatternRule(Pattern mPattern  ) {
        this.mPattern = mPattern;
     }

    public Pattern getmPattern() {
        return mPattern;

    }

    public void setmPattern(Pattern mPattern) {
        this.mPattern = mPattern;
    }

    public Rule build(){
        return new Rule(this);
    }

 }
