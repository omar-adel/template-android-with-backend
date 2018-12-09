package com.basgeekball.awesomevalidation.utility;

import com.basgeekball.awesomevalidation.ValidationHolder;

public interface ValidationCallback {

    void execute(ValidationHolder validationHolder, String errorMessage );

}