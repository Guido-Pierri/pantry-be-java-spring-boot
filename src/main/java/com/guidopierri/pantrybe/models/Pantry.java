package com.guidopierri.pantrybe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Entity
@Setter
@Getter
@Table(name = "pantry")
@ToString(exclude = "items")
public class Pantry {
    @Getter
    @Setter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @OneToMany(mappedBy = "pantry")
    private List<Item> items;
    @Setter
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public void addItem(Item item) {
        items.add(item);
    }


}
