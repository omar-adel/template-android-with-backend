package com.basgeekball.awesomevalidation.validators;

import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

public class BasicValidator extends Validator {

    private ValidationCallback mValidationCallback = new ValidationCallback() {
        @Override
        public void execute(ValidationHolder validationHolder, String errorMessage) {
            validationHolder.getEditText().setError(errorMessage);
        }
    };

    @Override
    public boolean trigger() {
        clear();
        return checkFields(mValidationCallback,false);
     }

    @Override
    public boolean triggerFirst() {
        clear();
        return checkFields(mValidationCallback,true);
    }

    @Override
    public boolean triggerCurrent(EditText editText) {
        return checkFields(mValidationCallback, editText);
    }

    @Override
    public void clear() {
        for (ValidationHolder validationHolder : mValidationHolderList) {
            validationHolder.getEditText().setError(null);
        }
    }


}