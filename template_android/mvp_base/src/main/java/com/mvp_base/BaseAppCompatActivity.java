package com.mvp_base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;


public abstract class BaseAppCompatActivity<P extends Base.IPresenter>
        extends AppCompatActivity
        implements Base.IViewAct<P> {
    private P mPresenter;

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if ( v instanceof EditText) {
//                Rect outRect = new Rect();
//                v.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                    Log.e("focus", "touchevent");
//                    v.clearFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//        }
//        return super.dispatchTouchEvent(event);
//    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            try {
                getWindow().getDecorView().setImportantForAutofill(
                        View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
            }
            catch (Exception e)
            {
                //   Log.e("ExceptionSearchAutoFill ",e.toString());
            }

        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResource());
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup v = ((ViewGroup) findViewById(android.R.id.content));
        if (v != null) {
            if (getExtraLayout() > 0) {
                View extraView = layoutInflater.inflate(getExtraLayout(), null);
                ViewGroup contentView = (ViewGroup) v.findViewById(getContainerID());
                if (contentView != null)
                    contentView.addView(extraView);
                ButterKnife.bind(this, v);
            } else
                ButterKnife.bind(this);
        } else
            ButterKnife.bind(this);

        mPresenter = injectDependencies();

        if (getPresenter() == null) {
            throw new IllegalArgumentException("You must inject the " +
                    "dependencies before retrieving the presenter");
        } else {

            configureUI();
        }

     }


    @Override
    protected void onDestroy() {
        super.onDestroy();
     }

    @Override
    public P getPresenter() {
        return mPresenter;
    }


}
