package com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils;

public class AppsBean {
    String packageName;
    String name;
    String description;
    String icon_url;
    String rating;
    String interstitial;
    public AppsBean(){
        super();
    }

    public AppsBean(String packageName, String name, String description, String icon_url, String rating, String interstitial) {
        this.packageName = packageName;
        this.name = name;
        this.description = description;
        this.icon_url = icon_url;
        this.rating = rating;
        this.interstitial = interstitial;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getInterstitial() {
        return interstitial;
    }

    public void setInterstitial(String interstitial) {
        this.interstitial = interstitial;
    }
}
