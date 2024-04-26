package com.guidopierri.pantrybe.models;

import com.guidopierri.pantrybe.permissions.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "application_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 4, max = 64)

    private String username;
    private String email;
    @Size(min = 4, max = 64)
    private String password;
    private String firstName;
    private String lastName;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @OneToOne(mappedBy = "user")
    private Pantry pantry;

    public User() {
    }

    public User(String username, String email, String password, String firstName, String lastName, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled, Roles roles, Pantry pantry) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.roles = roles;
        this.pantry = pantry;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.getGrantedAuthorities();
    }
    public long getId() {
        return id;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail(String email) {
        return email;
    }
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setAuthority(Roles roles) {
        this.roles = roles;
    }

    public void setPantry(Pantry pantry) {
        this.pantry = pantry;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Pantry getPantry() {
        return pantry;
    }

    public String getEmail() {
        return email;
    }
}
