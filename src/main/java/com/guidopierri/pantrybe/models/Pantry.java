package com.guidopierri.pantrybe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity

@Table(name = "pantry")
public class Pantry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "pantry")
    private List<Item> items;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addItem(Item item) {
        items.add(item);
    }


    public String toString() {
        return "Pantry(id=" + this.getId() + ", user=" + this.user + ")";
    }
}
