package com.example.airbnbb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    public Role(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

}
