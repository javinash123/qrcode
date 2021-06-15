package com.simprosys.scan.qrcode.barcode.reader.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.Log;

import java.util.Locale;

public class LanguageHelper {
    private static final String SELECTED_LANGUAGE = "Language.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        return setLanguage(context, lang);
    }
    public static Context onAttach(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        return setLanguage(context, lang);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }
    public static Context setLanguage(Context context, String language) {
        persist(context, language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    public static String getPersistedData(Context context, String defaultLanguage) {
        return SharedPreferenceHelper.getSharedPreferenceString(context, SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferenceHelper.setSharedPreferenceString(context, SELECTED_LANGUAGE, language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale /*= new Locale(language)*/;

        Log.e("TAG_", "Device Language N : " + language);

        switch (language) {
            case "zh-rCN":
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case "zh-rTW":
                locale = Locale.TRADITIONAL_CHINESE;
                break;
            default:
                locale = new Locale(language);
                break;
        }

        Configuration configuration = context.getResources().getConfiguration();

        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);

        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale;

        Log.e("TAG_", "Device Language : " + language);

        switch (language) {
            case "zh-rCN":
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case "zh-rTW":
                locale = Locale.TRADITIONAL_CHINESE;
                break;
            default:
                locale = new Locale(language);
                break;
        }

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
}
