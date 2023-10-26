package com.example.test2android.model;

public class WorkItem {
    private int genderIcon;
    private String name;
    private String title;
    private String date;

    public WorkItem() {
    }

    public WorkItem(int genderIcon, String name, String title, String date) {
        this.genderIcon = genderIcon;
        this.name = name;
        this.title = title;
        this.date = date;
    }

    public int getGenderIcon() {
        return genderIcon;
    }

    public void setGenderIcon(int genderIcon) {
        this.genderIcon = genderIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
