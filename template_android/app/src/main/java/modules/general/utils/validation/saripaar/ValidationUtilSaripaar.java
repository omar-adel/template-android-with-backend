package modules.general.utils.validation.saripaar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import modules.general.utils.validation.listeners.OnValidationCallBack;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;


/**
 * Created by Net22 on 11/16/2017.
 */

public class ValidationUtilSaripaar implements Validator.ValidationListener

        , View.OnFocusChangeListener {

    private Context context;
    private Validator validator;
    private OnValidationCallBack onValidationCallBack;

    public Validator getValidator() {
        return validator;
    }


    public ValidationUtilSaripaar(Context context, OnValidationCallBack onValidationCallBack) {
        validator = new Validator(context);
        this.context = context;
        this.onValidationCallBack = onValidationCallBack;
        setImmediateMode();
        //       setBurstMode();
        this.validator.setValidationListener(this);
    }

    public ValidationUtilSaripaar(Fragment fragment, OnValidationCallBack onValidationCallBack) {
        validator = new Validator(fragment);
        this.context = fragment.getActivity();
        this.onValidationCallBack = onValidationCallBack;
        setImmediateMode();
        //       setBurstMode();
        this.validator.setValidationListener(this);
    }

    public void setImmediateMode() {
        this.validator.setValidationMode(Validator.Mode.IMMEDIATE);//show error at first field that has error only
    }

    public void setBurstMode() {
        this.validator.setValidationMode(Validator.Mode.BURST);
    }

    public void validateAll() {
        setBurstMode();
        validator.validate();
    }

    public void validateAllFirstError() {
        setImmediateMode();
        validator.validate();
    }


    @Override
    public void onValidationSucceeded() {
        //Toast.makeText(context, "Form Successfully Validated", Toast.LENGTH_SHORT).show();
        onValidationCallBack.onValidationSucceeded();
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getFailedRules().get(0).getMessage(context);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            setImmediateMode();
            validator.validateBefore(view);
        }
    }


}
