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

    @OneToMany(mappedBy = "pantry", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User application_user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public User getApplication_user() {
        return application_user;
    }

    public void setUser(User user) {
        this.application_user = user;
    }

    public void addItem(Item item) {
        items.add(item);
    }


    public String toString() {
        return "Pantry(id=" + this.getId() + ", user=" + this.application_user + ")";
    }
}
