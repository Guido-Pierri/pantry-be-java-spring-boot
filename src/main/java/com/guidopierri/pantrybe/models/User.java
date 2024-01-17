package com.guidopierri.pantrybe.models;

import jakarta.persistence.*;

@Entity
@Table(name = "_user")
public class User {
    /*@OneToMany(fetch= FetchType.LAZY, mappedBy = "user")
    private List<CustomItem> customItemIds;*/
    /*@OneToMany(fetch= FetchType.LAZY, mappedBy = "user")
    private List<Recipe> recipes;*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToOne(mappedBy = "user")
    private Pantry pantry;

    public User(Long id, String firstName, String lastName, String email, String password, Pantry pantry) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pantry = pantry;
    }

    public User() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
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

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Pantry getPantry() {
        return this.pantry;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPantry(Pantry pantry) {
        this.pantry = pantry;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class UserBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Pantry pantry;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder pantry(Pantry pantry) {
            this.pantry = pantry;
            return this;
        }

        public User build() {
            return new User(this.id, this.firstName, this.lastName, this.email, this.password, this.pantry);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", password=" + this.password + ", pantry=" + this.pantry + ")";
        }
    }
}
