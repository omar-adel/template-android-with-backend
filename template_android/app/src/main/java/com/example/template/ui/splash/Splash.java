package com.example.template.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.template.R;
import com.example.template.ui.TestRestApi.RestApiListAct;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.ComplexOperations.ComplexOperations;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.SqliteListAct;
import com.example.template.ui.TestSqliteDbflow.notes.NotesActivity;
import com.example.template.ui.TestSqliteDbflow.simpleitems.SimpleItemActivity;
import com.example.template.ui.maps.MapControlActivity;
import com.example.template.ui.maps.MapDisplayActivity;
import com.example.template.ui.socketTest.SocketMainActivity;
import com.example.template.ui.splash.presenter.ISplashContract;
import com.example.template.ui.splash.presenter.SplashPresenter;
import com.mvp_base.BaseAppCompatActivity;

import modules.general.utils.LanguageUtil;


public class Splash extends BaseAppCompatActivity<ISplashContract.ISplashPresenter> implements ISplashContract.ISplashView {
    private final int SPLASH_DISPLAY_LENGHT = 1000;
    Intent myIntent;

    @Override
    public int getLayoutResource() {
        return R.layout.act_splash;
    }

    @Override
    public int getExtraLayout() {
        return 0;
    }

    @Override
    public int getContainerID() {
        return 0;
    }

    @Override
    public void configureUI() {
        LanguageUtil.setAppLanguage(getApplicationContext(),  LanguageUtil.ENGLISH_LANGUAGE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

           myIntent = new Intent(Splash.this, RestApiListAct.class);
           //  myIntent = new Intent(Splash.this, SqliteListAct.class);
              // myIntent = new Intent(Splash.this, ComplexOperations.class);
              //    myIntent = new Intent(Splash.this, SimpleItemActivity.class);
                 // myIntent = new Intent(Splash.this, NotesActivity.class);
//
                // myIntent = new Intent(Splash.this, SocketMainActivity.class);
          //     myIntent = new Intent(Splash.this, MapDisplayActivity.class);
      //   myIntent = new Intent(Splash.this, MapControlActivity.class);

                startActivity(myIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    @Override
    public ISplashContract.ISplashPresenter injectDependencies() {
        return new SplashPresenter(this, this);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        String lang_code = LanguageUtil.getAppLanguage(); //load it from SharedPref
        Context context = LanguageUtil.setAppLanguage(newBase, lang_code);
        super.attachBaseContext(context);
    }

}