package com.guidopierri.pantrybe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DabasItem implements GenericItem{
    private long id;
    private String gtin;
    private String name;
    private long quantity;
    private String expirationDate;
    private String brand;
    private String image;
    private String category;

    private Pantry pantry;

    public DabasItem() {
    }

    public DabasItem(String name, String gtin, long quantity, String expirationDate, String brand, String image, String category, Pantry pantry) {
        this.name = name;
        this.gtin = gtin;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.brand = brand;
        this.image = image;
        this.category = category;
        this.pantry = pantry;
    }

    @Override
    public String toString() {
        return "CustomItem{" +
                "id=" + id +
                ", gtin='" + gtin + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", expirationDate='" + expirationDate + '\'' +
                ", brand='" + brand + '\'' +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", pantry=" + pantry +
                '}';
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getImage() {
        return this.image;
    }

    public String getCategory() {
        return this.category;
    }

    public Pantry getPantry() {
        return this.pantry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonIgnore
    public void setPantry(Pantry pantry) {
        this.pantry = pantry;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }
}
