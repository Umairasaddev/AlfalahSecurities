package com.softech.bipldirect.Models;

/**
 * Developed by Hasham.Tahir on 1/28/2016.
 */
public class Menu {

    private int ic_resource;
    private String title;
    private boolean selected;

    public Menu(String title, int ic_resource, boolean selected) {
        this.title = title;
        this.ic_resource = ic_resource;
        this.selected = selected;
    }

    public int getIc_resource() {
        return ic_resource;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return selected;
    }
}
