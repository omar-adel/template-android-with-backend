package com.basgeekball.awesomevalidation.model;

/**
 * Created by Net22 on 11/19/2017.
 */

public class Rule {

    private PatternRule patternRule;
    private NumericRangeRule mNumericRangeRule;
    private IsEmptyRule isEmptyRule;
    private LengthRule mLengthRule;
    private IsEqualRule isEqualRule;
    private int sequence;

    public Rule()
    {

    }
    public Rule(PatternRule patternRule)
    {
        this.patternRule = patternRule;
    }
    public Rule(NumericRangeRule mNumericRangeRule)
    {
        this.mNumericRangeRule = mNumericRangeRule;

    }
    public Rule(IsEmptyRule isEmptyRule)
    {
        this.isEmptyRule = isEmptyRule;

    }
    public Rule(LengthRule mLengthRule)
    {
        this.mLengthRule = mLengthRule;

    }
    public Rule(IsEqualRule isEqualRule)
    {
        this.isEqualRule = isEqualRule;
    }

    public Rule(PatternRule patternRule, NumericRangeRule mNumericRangeRule, IsEmptyRule isEmptyRule, LengthRule mLengthRule
            , IsEqualRule isEqualRule , int sequence) {
        this.patternRule = patternRule;
        this.mNumericRangeRule = mNumericRangeRule;
        this.isEmptyRule = isEmptyRule;
        this.mLengthRule = mLengthRule;
        this.isEqualRule = isEqualRule;
        this.sequence = sequence;
    }


    public IsEqualRule getIsEqualRule() {
        return isEqualRule;
    }

    public void setIsEqualRule(IsEqualRule isEqualRule) {
        this.isEqualRule = isEqualRule;
    }


    public PatternRule getPatternRule() {
        return patternRule;
    }

    public void setPatternRule(PatternRule patternRule) {
        this.patternRule = patternRule;
    }

    public IsEmptyRule getIsEmptyRule() {
        return isEmptyRule;
    }

    public void setIsEmptyRule(IsEmptyRule isEmptyRule) {
        this.isEmptyRule = isEmptyRule;
    }

    public PatternRule getmPattern() {
        return patternRule;
    }

    public void setmPattern(PatternRule mPattern) {
        this.patternRule = mPattern;
    }

    public NumericRangeRule getmNumericRangeRule() {
        return mNumericRangeRule;
    }

    public void setmNumericRangeRule(NumericRangeRule mNumericRangeRule) {
        this.mNumericRangeRule = mNumericRangeRule;
    }



    public LengthRule getmLengthRule() {
        return mLengthRule;
    }

    public void setmLengthRule(LengthRule mLengthRule) {
        this.mLengthRule = mLengthRule;
    }


    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }


}
