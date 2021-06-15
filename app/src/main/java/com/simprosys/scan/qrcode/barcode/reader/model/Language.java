package com.simprosys.scan.qrcode.barcode.reader.model;

/**
 * Created by Vinay on 21/6/17.
 */

public class Language {
    private String languageName;
    private String languageCode;
    private String languageNameInDefaultLocale;

    public Language(String languageCode, String languageName) {
        this.languageCode = languageCode;
        this.languageName = languageName;
    }

    public Language(String languageCode, String languageName, String languageNameInDefaultLocale) {
        setLanguageCode(languageCode);
        setLanguageName(languageName);
        setLanguageNameInDefaultLocale(languageNameInDefaultLocale);
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageNameInDefaultLocale() {
        return languageNameInDefaultLocale;
    }

    public void setLanguageNameInDefaultLocale(String languageNameInDefaultLocale) {
        this.languageNameInDefaultLocale = languageNameInDefaultLocale;
    }

    @Override
    public String toString() {
        return "Language{" +
                "languageName='" + languageName + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", languageNameInDefaultLocale='" + languageNameInDefaultLocale + '\'' +
                '}';
    }
}
