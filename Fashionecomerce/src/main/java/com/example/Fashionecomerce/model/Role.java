package com.example.Fashionecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    private Role(String name) {
        this.Name = name;
    }
}
