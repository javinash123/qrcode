package com.simprosys.scan.qrcode.barcode.reader.model;

import java.io.Serializable;

/**
 * Created by simprosys on 1/2/19.
 */

public class QRGenerateItem implements Serializable{
    public int Image;
    public String name;

    public QRGenerateItem(int image, String name) {
        Image = image;
        this.name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "QRGenerateItem{" +
                "Image=" + Image +
                ", name='" + name + '\'' +
                '}';
    }
}
