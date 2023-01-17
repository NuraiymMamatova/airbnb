package com.example.airbnbb7.db.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @SequenceGenerator(name = "role_gen", sequenceName = "role_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    private Long id;

    private String nameOfRole;

    @ManyToMany(targetEntity = User.class, cascade = {MERGE, REFRESH, DETACH})
    @JoinTable(name = "roles_users", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> users;

    public void addRole(User user) {
        if (user == null) users = new ArrayList<>();
        users.add(user);
    }

    public Role(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

}
