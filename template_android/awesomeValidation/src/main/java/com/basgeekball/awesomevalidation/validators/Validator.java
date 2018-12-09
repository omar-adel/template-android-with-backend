package com.basgeekball.awesomevalidation.validators;

import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.helper.SortList;
import com.basgeekball.awesomevalidation.model.LengthRule;
import com.basgeekball.awesomevalidation.model.Rule;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import java.util.ArrayList;
import java.util.regex.Matcher;

public abstract class Validator {

    public ArrayList<ValidationHolder> mValidationHolderList;
    private boolean mHasFailed = false;

    Validator() {
        mValidationHolderList = new ArrayList<>();
    }

    public void set(EditText editText, ArrayList<Rule> rules) {
        ValidationHolder validationHolder = new ValidationHolder(editText, rules);
        mValidationHolderList.add(validationHolder);
    }

    public void remove(EditText editText) {
        for (int i = 0; i <mValidationHolderList.size() ; i++) {
            if(mValidationHolderList.get(i).getEditText()==editText)
            {
                mValidationHolderList.remove(i);
                break;
            }
        }
    }

    public void set(EditText confirmationEditText, EditText editText, ArrayList<Rule> rules) {
        ValidationHolder validationHolder = new ValidationHolder(confirmationEditText, editText, rules);
        mValidationHolderList.add(validationHolder);
    }

    public boolean checkFields(ValidationCallback callback,EditText editText)
    {
        boolean result = true;
        mHasFailed = false;
        boolean foundEditText=false;
        for (ValidationHolder validationHolder : mValidationHolderList) {
            if(validationHolder.getEditText()==editText)
            {
                result = checkErrorEditText(validationHolder, callback);
                foundEditText=true;
                break;
            }
        }
        if(!foundEditText)
        {
            for (ValidationHolder validationHolder : mValidationHolderList) {
                if(validationHolder.getmConfirmationEditText()!=null)
                {
                    if(validationHolder.getmConfirmationEditText()==editText)
                    {
                        result = checkErrorEditText(validationHolder, callback);
                         break;
                    }
                }

            }
        }
        return result;
    }

    public boolean checkFields(ValidationCallback callback,boolean firstOnly) {
        boolean finalResult = true;
        boolean result = true;
        mHasFailed = false;
        for (ValidationHolder validationHolder : mValidationHolderList) {
            if(firstOnly)
            {
                if(!finalResult)
                {
                    break;
                }
                else
                {
                    finalResult = checkErrorEditText(validationHolder, callback);
                }
            }
            else
            {
                if(!result)
                {
                    finalResult=false;
                }
                 result = checkErrorEditText(validationHolder, callback);
            }
        }
        return finalResult;
    }

    public boolean checkErrorEditText(ValidationHolder validationHolder , ValidationCallback callback )
    {
        boolean result = true;
        ArrayList<Rule> rules = validationHolder.getmRules();
        SortList.sortRules(rules);
        for (int i = 0; i <rules.size() ; i++) {
            if(!result) {
                break;
            }
            else
            {
                if(rules.get(i).getmLengthRule()!=null)
                {
                    result = checkLengthTypeField(i,validationHolder, callback) && result;
                }
                else
                if(rules.get(i).getmNumericRangeRule()!=null)
                {
                    result = checkRangeTypeField(i,validationHolder, callback) && result;
                }
                else
                if(rules.get(i).getmPattern()!=null)
                {
                    result = checkRegexTypeField(i,validationHolder, callback) && result;
                }
                else
                if(rules.get(i).getIsEmptyRule()!=null)
                {
                    result = checkEmptyTypeField(i,validationHolder, callback) && result;

                }
                else
                if(rules.get(i).getIsEqualRule()!=null)
                {
                    result = checkConfirmationTypeField(i , validationHolder, callback) && result;
                }
            }

        }

        return  result ;
    }
    private boolean checkRegexTypeField(int ruleIndex ,ValidationHolder validationHolder, ValidationCallback callback) {
        Matcher matcher = validationHolder.getmRules().get(ruleIndex).getPatternRule().getmPattern().matcher(validationHolder.getText());
        if (!matcher.matches()) {
            String errorMessage =validationHolder.getmRules().get(ruleIndex).getPatternRule().getErrMsg();
            executeCallback(callback, validationHolder, errorMessage);
            return false;
        }
        return true;
    }

    private boolean checkLengthTypeField(int ruleIndex ,ValidationHolder validationHolder, ValidationCallback callback) {
        boolean valid=false;
        LengthRule mLengthRule =validationHolder.getmRules().get(ruleIndex).getmLengthRule();
        if(
                (mLengthRule.getMinLength()<=validationHolder.getText().toString().length())
            &&
              (mLengthRule.getMaxLength()>=validationHolder.getText().toString().length())
                )
        {
            valid=true;
        }

        if (!valid) {
            String errorMessage =validationHolder.getmRules().get(ruleIndex).getmLengthRule().getErrMsg();
            executeCallback(callback, validationHolder, errorMessage);
            return false;
        }
        return true;
    }

    private boolean checkEmptyTypeField(int ruleIndex ,ValidationHolder validationHolder, ValidationCallback callback) {
        boolean valid = !validationHolder.getText().isEmpty();
        if (!valid) {
            String errorMessage =validationHolder.getmRules().get(ruleIndex).getIsEmptyRule().getErrMsg();
            executeCallback(callback, validationHolder, errorMessage);
            return false;
        }
        return true;
    }

    private boolean checkRangeTypeField(int ruleIndex ,ValidationHolder validationHolder, ValidationCallback callback) {
        boolean valid;
        try {
            valid = validationHolder.getmRules().get(ruleIndex).getmNumericRangeRule().isValid(validationHolder.getText());
        } catch (NumberFormatException e) {
            valid = false;
        }
        if (!valid) {
            String errorMessage =validationHolder.getmRules().get(ruleIndex).getmNumericRangeRule().getErrMsg();
            executeCallback(callback, validationHolder, errorMessage);
            return false;
        }
        return true;
    }

    private boolean checkConfirmationTypeField(int ruleIndex , ValidationHolder validationHolder, ValidationCallback callback) {
        boolean valid = validationHolder.getText().equals(validationHolder.getConfirmationText());
        if (!valid) {
            String errorMessage =validationHolder.getmRules().get(ruleIndex).getIsEqualRule().getErrMsg();
            executeCallback(callback, validationHolder, errorMessage);
             return false;
        }
        return true;
    }

    private void executeCallback(ValidationCallback callback, ValidationHolder validationHolder, String errorMessage) {
        callback.execute(validationHolder, errorMessage);
        //requestFocus(validationHolder);
    }

    private void requestFocus(ValidationHolder validationHolder) {
        if (!mHasFailed) {
            EditText editText = validationHolder.getEditText();
            editText.requestFocus();
            editText.setSelection(editText.getText().length());
            mHasFailed = true;
        }
    }

    public abstract boolean trigger();
    public abstract boolean triggerFirst();
    public abstract boolean triggerCurrent(EditText editText);

    public abstract void clear();

}
