package com.example.quizkart.models;

public class Category {

    private String categoryId;
    private String categoryName;
    private String totalCourses;
    private String image;

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getTotalCourses() {
        return totalCourses;
    }

    public String getImage() {
        return image;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTotalCourses(String totalCourses) {
        this.totalCourses = totalCourses;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
