package com.example.herbaldrugsdemo.DataModels;

public class FakeCategory {
    private String titlecategory;
    private String imagecategory;

    public FakeCategory(String titlecategory, String imagecategory) {
        this.titlecategory = titlecategory;
        this.imagecategory = imagecategory;
    }

    public FakeCategory() {
    }

    public String getTitlecategory() {
        return titlecategory;
    }

    public void setTitlecategory(String titlecategory) {
        this.titlecategory = titlecategory;
    }

    public String getImagecategory() {
        return imagecategory;
    }

    public void setImagecategory(String imagecategory) {
        this.imagecategory = imagecategory;
    }
}
