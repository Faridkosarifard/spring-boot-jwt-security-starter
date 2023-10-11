package com.example.demo.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 2:12 PM
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
    public Privilege(final String name) {
        super();
        this.name = name;
    }
}
