package com.tool.toolsshare.model;

public class Items {
    String name,location,rating,price;
    int image;
    boolean isFavourite;

    public Items(String name, String location, String rating, String price, int image, boolean isFavourite) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.price = price;
        this.image = image;
        this.isFavourite = isFavourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
