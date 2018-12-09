package modules.general.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.example.template.R;

import java.util.Locale;

import modules.general.model.shareddata.Prefs;

/**
 * Created by omar on 6/15/2016.
 */
public class LanguageUtil {

    public static final String LanguageState="LanguageState";
    public static String ARABIC_LANGUAGE="ar";
    public static String ENGLISH_LANGUAGE="en";

    public static Context setAppLanguage(Context context, String localeLang) {
        Prefs.putString(LanguageState,localeLang);
        return setAppLocale(context, localeLang);
    }

    public static String getAppLanguage( )

    {
        String appLanguage = Prefs.getString(LanguageState,ENGLISH_LANGUAGE);
        return appLanguage;
    }



    public static Context setAppLocale(Context context, String localeLang) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, localeLang);
        }

        return updateResourcesLegacy(context, localeLang);

    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    public static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

    public static String getDeviceLocale()

    {
        Log.e("getDeviceLocale", Locale.getDefault().getISO3Language());
        return Locale.getDefault().getISO3Language();
    }



}
