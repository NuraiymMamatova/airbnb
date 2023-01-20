package com.example.airbnbb7.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    private Long id;

    private String name;

    private String email;

    private String password;

    private String image;

    private Long countOfBookedHouse;

    @ManyToMany(cascade = {REFRESH, DETACH, MERGE, REMOVE}, mappedBy = "guests")
    private List<House> houses;

    @ManyToMany(cascade = {REFRESH, DETACH, MERGE, REMOVE}, mappedBy = "users")
    private List<Booking> bookings;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = {REFRESH, DETACH, MERGE, PERSIST}, mappedBy = "users")
    private List<Role> roles;

    public void addRole(Role role) {
        if (roles == null) roles = new ArrayList<>();
        roles.add(role);
    }

    @OneToMany(cascade = {REFRESH, DETACH, MERGE, REMOVE})
    private List<FavoriteHouse> favoriteHouses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getNameOfRole()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
