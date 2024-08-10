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
    private String product_classifications;
    private String brute_weight;
    private String drained_weight;
    /**
     * The level of the item, can be "bas", "mellan" or "topp"
     */
    private String level;

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

    public String getProduct_classifications() {
        return product_classifications;
    }

    public void setProduct_classifications(String product_classifications) {
        this.product_classifications = product_classifications;
    }

    public String getBrute_weight() {
        return brute_weight;
    }

    public void setBrute_weight(String brute_weight) {
        this.brute_weight = brute_weight;
    }

    public String getDrained_weight() {
        return drained_weight;
    }

    public void setDrained_weight(String drained_weight) {
        this.drained_weight = drained_weight;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
                ", product_classifications='" + product_classifications + '\'' +
                ", brute_weight='" + brute_weight + '\'' +
                ", drained_weight='" + drained_weight + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
