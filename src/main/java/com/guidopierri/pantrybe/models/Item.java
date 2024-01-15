package com.guidopierri.pantrybe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@ToString(exclude = "pantry")
public class Item {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private long quantity;
    @Getter
    @Setter
    private String expirationDate;
    @Setter
    @Getter
    private long gtin;
    @Setter
    @Getter
    private String brand;
    @Setter
    @Getter
    private String image;
    @Setter
    @Getter
    private String category;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "pantry")
    @Getter
    @Setter
    private Pantry pantry;
}
