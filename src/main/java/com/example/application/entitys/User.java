package com.example.application.entitys;

import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @NotBlank
    //@Size(min = 3, max = 20)
    private String username;
    @NotBlank
    //@Size(min = 6, max = 40)
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set <Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Measurement> measurements;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", username=" + username + ", passwordHash=" + passwordHash + ", roles=" + roles + '}';
    }

}
