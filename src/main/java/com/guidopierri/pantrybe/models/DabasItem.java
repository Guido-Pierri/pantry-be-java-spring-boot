package com.guidopierri.pantrybe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DabasItem implements GenericItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String gtin;
    private String name;
    private String brand;
    private String image;
    private String category;
    private String size;
    @Column(columnDefinition = "VARCHAR(MAX)")
    private String ingredients;
    private String productClassifications;
    private String bruteWeight;
    private String drainedWeight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getProductClassifications() {
        return productClassifications;
    }

    public void setProductClassifications(String productClassifications) {
        this.productClassifications = productClassifications;
    }

    public String getBruteWeight() {
        return bruteWeight;
    }

    public void setBruteWeight(String bruteWeight) {
        this.bruteWeight = bruteWeight;
    }

    public String getDrainedWeight() {
        return drainedWeight;
    }

    public void setDrainedWeight(String drainedWeight) {
        this.drainedWeight = drainedWeight;
    }

    @Override
    public String toString() {
        return "DabasItem{" +
                "id=" + id +
                ", gtin='" + gtin + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", size='" + size + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", productClassifications='" + productClassifications + '\'' +
                ", bruteWeight='" + bruteWeight + '\'' +
                ", drainedWeight='" + drainedWeight + '\'' +
                '}';
    }
}
