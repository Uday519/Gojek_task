package com.uday.gojek_task.models;

public class Avatars {

    public String url;
    public byte[] images;

    public Avatars(String url, byte[] images) {
        this.url = url;
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }
}
