package com.basgeekball.awesomevalidation;

import android.widget.EditText;

import com.basgeekball.awesomevalidation.model.Rule;
import com.basgeekball.awesomevalidation.validators.BasicValidator;
import com.basgeekball.awesomevalidation.validators.Validator;

import java.util.ArrayList;

public class AwesomeValidation {

    private Validator mValidator = null;

    public Validator getValidator() {
        return mValidator;
    }

    public AwesomeValidation( ) {
                if (mValidator == null || !(mValidator instanceof BasicValidator)) {
                    mValidator = new BasicValidator();
                }
                return;
    }

    public void addValidation(EditText editText, ArrayList<Rule>rules) {
         mValidator.set(editText, rules);
    }

    public void removeValidation(EditText editText) {
        mValidator.remove(editText);
    }



    public void addValidation(EditText confirmationEditText, EditText editText,  ArrayList<Rule>rules) {
         mValidator.set(confirmationEditText, editText, rules);
    }

    public boolean validate() {
        return mValidator.trigger();
    }

    public boolean validateFirstError() {
        return mValidator.triggerFirst();
    }
    public boolean validateCurrent(EditText editText) {
        return mValidator.triggerCurrent(editText);
    }


    public void clear() {
        mValidator.clear();
    }

}
