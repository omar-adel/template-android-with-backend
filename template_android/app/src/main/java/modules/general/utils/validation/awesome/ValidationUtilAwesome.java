package modules.general.utils.validation.awesome;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import modules.general.utils.validation.listeners.OnValidationCallBack;

/**
 * Created by Net22 on 11/19/2017.
 */

public class ValidationUtilAwesome implements

        View.OnFocusChangeListener {

    Context context;
    private OnValidationCallBack onValidationCallBack;

    public ValidationUtilAwesome(Context context, OnValidationCallBack onValidationCallBack) {
        this.context = context;
        this.onValidationCallBack = onValidationCallBack;
        initValidation();
    }

    private AwesomeValidation mAwesomeValidation;

    public AwesomeValidation getAwesomeValidation() {
        return mAwesomeValidation;
    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!((EditText) view).getText().toString().isEmpty()) {
            if (!hasFocus) {
                mAwesomeValidation.validateCurrent((EditText) view);
            }
        } else {

        }

    }

    public void addOnFocusChangeListeners() {
        for (int i = 0; i <
                mAwesomeValidation.getValidator().mValidationHolderList.size()
                ; i++) {
            mAwesomeValidation.getValidator().mValidationHolderList.get(i).getEditText()
                    .setOnFocusChangeListener(this);
        }
    }

    public void validateAll() {
        if (mAwesomeValidation.validate()) {
            //Toast.makeText(context, "Form Successfully Validated", Toast.LENGTH_SHORT).show();
            onValidationCallBack.onValidationSucceeded();
        } else {

        }
    }

    public void validateAllFirstError() {
        if (mAwesomeValidation.validateFirstError()) {
            // Toast.makeText(context, "Form Successfully Validated", Toast.LENGTH_SHORT).show();
            onValidationCallBack.onValidationSucceeded();
        } else {

        }
    }

    private void initValidation() {
        mAwesomeValidation = new AwesomeValidation();
    }

    private void clearValidation() {
        if (mAwesomeValidation != null) {
            mAwesomeValidation.clear();
        }
    }

}
