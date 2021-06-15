package com.simprosys.scan.qrcode.barcode.reader.model;

/**
 * Created by Pankaj on 03-11-2017.
 */

public class Theme {
    private int id;
    private String colorName;
    private int primaryColor;
    private int primaryDarkColor;
    private int accentColor;

    public Theme(int primaryColor, int primaryDarkColor, int accentColor) {
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.accentColor = accentColor;
    }

    public Theme(int id , int primaryColor, int primaryDarkColor, int accentColor) {
        this.id = id;
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.accentColor = accentColor;
    }

    public Theme(int id, String colorName, int primaryColor, int primaryDarkColor, int accentColor) {
        this.id = id;
        this.colorName = colorName;
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.accentColor = accentColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public int getPrimaryDarkColor() {
        return primaryDarkColor;
    }

    public void setPrimaryDarkColor(int primaryDarkColor) {
        this.primaryDarkColor = primaryDarkColor;
    }

    public int getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(int accentColor) {
        this.accentColor = accentColor;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", colorName='" + colorName + '\'' +
                ", primaryColor=" + primaryColor +
                ", primaryDarkColor=" + primaryDarkColor +
                ", accentColor=" + accentColor +
                '}';
    }
}
