package com.simprosys.scan.qrcode.barcode.reader.utility;

import android.content.Context;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.model.Theme;

import java.util.ArrayList;

/**
 * Created by Pankaj on 12-11-2017.
 */

public class ThemeUtil {
    public static final int THEME_INDIGO = 0;
    public static final int THEME_RED = 1;
    public static final int THEME_PURPLE = 2;
    public static final int THEME_DEEPPURPLE = 3;
    public static final int THEME_ELECTRICPURPLE = 4;
    public static final int THEME_CYAN = 5;
    public static final int THEME_GREEN = 6;
    public static final int THEME_BROWN = 7;
    public static final int THEME_BLUEGRAY = 8;

    public static int getThemeId(int theme){
        int themeId = 0;
        switch (theme){
            case THEME_INDIGO  :
                themeId = R.style.AppTheme_Indigo;
                break;
            case THEME_RED  :
                themeId = R.style.AppTheme_Red;
                break;
            case THEME_PURPLE  :
                themeId = R.style.AppTheme_Purple;
                break;
            case THEME_DEEPPURPLE  :
                themeId = R.style.AppTheme_DeepPurple;
                break;
            case THEME_ELECTRICPURPLE  :
                themeId = R.style.AppTheme_ElectricPurple;
                break;
            case THEME_CYAN  :
                themeId = R.style.AppTheme_Cyan;
                break;
            case THEME_GREEN  :
                themeId = R.style.AppTheme_Green;
                break;
            case THEME_BROWN  :
                themeId = R.style.AppTheme_Brown;
                break;
            case THEME_BLUEGRAY  :
                themeId = R.style.AppTheme_BlueGrey;
                break;
        }
        return themeId;
    }

    public static ArrayList<Theme> getThemeList(Context context){
        ArrayList<Theme> mListItem = new ArrayList<>();
        mListItem.add(new Theme(0, context.getResources().getString(R.string.colorNameIndigo),
                R.color.primaryDarkColorIndigo,
                R.color.primaryDarkColorIndigo,
                R.color.accentColorIndigo));
        mListItem.add(new Theme(1, context.getResources().getString(R.string.colorNameRed),
                R.color.primaryDarkColorRed,
                R.color.primaryDarkColorRed,
                R.color.accentColorRed));
        mListItem.add(new Theme(2, context.getResources().getString(R.string.colorNamePurple),
                R.color.primaryDarkColorPurple,
                R.color.primaryDarkColorPurple,
                R.color.accentColorPurple));
        mListItem.add(new Theme(3, context.getResources().getString(R.string.colorNameDeepPurple),
                R.color.primaryDarkColorDeepPurple,
                R.color.primaryDarkColorDeepPurple,
                R.color.accentColorDeepPurple));
        mListItem.add(new Theme(4, context.getResources().getString(R.string.colorNameElectricPurple),
                R.color.primaryDarkColorElectricPurple,
                R.color.primaryDarkColorElectricPurple,
                R.color.accentColorElectricPurple));
        mListItem.add(new Theme(5, context.getResources().getString(R.string.colorNameCyan),
                R.color.primaryDarkColorCyan,
                R.color.primaryDarkColorCyan,
                R.color.accentColorCyan));
        mListItem.add(new Theme(6, context.getResources().getString(R.string.colorNameGreen),
                R.color.primaryDarkColorGreen,
                R.color.primaryDarkColorGreen,
                R.color.accentColorGreen));
        mListItem.add(new Theme(7, context.getResources().getString(R.string.colorNameBrown),
                R.color.primaryDarkColorBrown,
                R.color.primaryDarkColorBrown,
                R.color.accentColorBrown));
        mListItem.add(new Theme(8, context.getResources().getString(R.string.colorNameBlueGray),
                R.color.primaryDarkColorBlueGrey,
                R.color.primaryDarkColorBlueGrey,
                R.color.accentColorBlueGrey));

       return mListItem;
    }
}
