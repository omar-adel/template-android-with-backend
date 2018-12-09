package com.basgeekball.awesomevalidation;

import android.widget.EditText;

import com.basgeekball.awesomevalidation.model.Rule;

import java.util.ArrayList;

public class ValidationHolder {

    private EditText mEditText;
    private EditText mConfirmationEditText;
    private ArrayList<Rule> mRules;

    public EditText getmEditText() {
        return mEditText;
    }

    public void setmEditText(EditText mEditText) {
        this.mEditText = mEditText;
    }

    public EditText getmConfirmationEditText() {
        return mConfirmationEditText;
    }

    public void setmConfirmationEditText(EditText mConfirmationEditText) {
        this.mConfirmationEditText = mConfirmationEditText;
    }

    public ArrayList<Rule> getmRules() {
        return mRules;
    }

    public void setmRules(ArrayList<Rule> mRules) {
        this.mRules = mRules;
    }

    public ValidationHolder(EditText editText, ArrayList<Rule> mRules) {
        mEditText = editText;
        this.mRules=mRules;
      }

    public ValidationHolder(EditText confirmationEditText, EditText editText ,  ArrayList<Rule> mRules) {
        mConfirmationEditText = confirmationEditText;
        mEditText = editText;
        this.mRules=mRules;
    }



    public boolean isConfirmationType() {
        return mConfirmationEditText != null ;
    }

    public boolean isEditTextStyle() {
        return mEditText != null;
    }


    public String getText() {
        if (mEditText != null) {
            return mEditText.getText().toString();
        }  else {
            return null;
        }
    }

    public String getConfirmationText() {
        if (mConfirmationEditText != null) {
            return mConfirmationEditText.getText().toString();
        } else {
            return null;
        }
    }

    public EditText getEditText() {
        if (isEditTextStyle()) {
            return isConfirmationType() ? mConfirmationEditText : mEditText;
        } else {
            return null;
        }
    }



}
