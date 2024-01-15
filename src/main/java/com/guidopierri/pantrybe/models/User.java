package com.guidopierri.pantrybe.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    /*@OneToMany(fetch= FetchType.LAZY, mappedBy = "user")
    private List<CustomItem> customItemIds;*/
    /*@OneToMany(fetch= FetchType.LAZY, mappedBy = "user")
    private List<Recipe> recipes;*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String email;
    @Setter
    private String password;
    @OneToOne(mappedBy = "user")
    private Pantry pantry;
    //should be a list of items? this is a problem when I will want to show all the items of a user in the front end
    //I will have to make a request for each item
    //maybe I can make a request for all the items of a user and then filter them in the front end
    //or I can make a request for all the items of a user and then make a request for each item
    /*@OneToMany(fetch= FetchType.LAZY, mappedBy = "user")
    private List<Item> itemIds;*/

    //public void setItemIds(List<Item> itemIds) {
        //this.itemIds = itemIds;
    //}

    /*public List<CustomItem> getCustomItemIds() {
        return customItemIds;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +

                '}';
    }
}
