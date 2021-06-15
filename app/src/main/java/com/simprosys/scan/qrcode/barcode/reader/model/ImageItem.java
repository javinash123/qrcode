package com.simprosys.scan.qrcode.barcode.reader.model;

import android.graphics.Bitmap;

/**
 * Created by simprosys on 26/2/19.
 */

public class ImageItem {
    public int id;
    public int frameBackground;
    public Bitmap bitmap;
    public int imgLogo;
    public int imageId;

    public ImageItem(int id, int frameBackground, Bitmap bitmap, int imgLogo) {
        this.id = id;
        this.frameBackground = frameBackground;
        this.bitmap = bitmap;
        this.imgLogo = imgLogo;
    }

    public ImageItem(int frameBackground, Bitmap bitmap, int imgLogo) {
        this.frameBackground = frameBackground;
        this.bitmap = bitmap;
        this.imgLogo = imgLogo;
    }

    public ImageItem(int id, int imageId) {
        this.id = id;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrameBackground() {
        return frameBackground;
    }

    public void setFrameBackground(int frameBackground) {
        this.frameBackground = frameBackground;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(int imgLogo) {
        this.imgLogo = imgLogo;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "id=" + id +
                ", frameBackground=" + frameBackground +
                ", bitmap=" + bitmap +
                ", imgLogo=" + imgLogo +
                ", imageId=" + imageId +
                '}';
    }
}
