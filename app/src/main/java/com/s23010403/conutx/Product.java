package com.s23010403.conutx;

public class Product {
    private String name, description, category;
    private int price, image;

    public Product(String name, String description, int price, int image, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public int getImage() { return image; }
    public String getCategory() { return category; }
}
