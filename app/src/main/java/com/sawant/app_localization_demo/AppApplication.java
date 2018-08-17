package com.sawant.app_localization_demo;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class AppApplication extends Application {

    private static AppApplication instance;
    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    public AppApplication() {
    }


    public static AppApplication getInstance(){
        if(instance == null){
            instance = new AppApplication();
        }
        return instance;
    }

    public Context updateBaseContextLocale(Context context) {

        prefs = context.getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);

        String currentAppLangauge = prefs.getString(AppConstants.SELECTED_LANGUAGE,"");
        Locale locale;
        if(currentAppLangauge!=null){

            if(currentAppLangauge.isEmpty()){
                locale = new Locale(AppConstants.APP_ENGLISH_LOCALE,AppConstants.APP_COUNTRY_LOCALE);
            }else{
                if(currentAppLangauge.equalsIgnoreCase(AppConstants.APP_ENGLISH_LOCALE)){
                    locale = new Locale(currentAppLangauge,AppConstants.APP_COUNTRY_LOCALE);
                }else if(currentAppLangauge.equalsIgnoreCase(AppConstants.APP_MARATHI_LOCALE)){
                    locale = new Locale(currentAppLangauge,AppConstants.APP_REGIONAL_COUNTRY_LOCALE);
                }else if(currentAppLangauge.equalsIgnoreCase(AppConstants.APP_HINDI_LOCALE)){
                    locale = new Locale(currentAppLangauge,AppConstants.APP_REGIONAL_COUNTRY_LOCALE);
                }
                else{
                    locale = new Locale(currentAppLangauge,AppConstants.APP_COUNTRY_LOCALE);
                }

            }
        }else{
            locale = new Locale(AppConstants.APP_ENGLISH_LOCALE,AppConstants.APP_COUNTRY_LOCALE);
        }

//        Locale locale = getCurrentLocale(context);
//        Locale locale = new Locale(APPLICATION_LANGUAGE_HINDI,APPLICATION_LANGUAGE_COUNTRY);
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale);
        }

        return updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }
}
