package com.example.demo.domain.model;

import com.example.demo.domain.view.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jboss.aerogear.security.otp.api.Base32;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 2:11 PM
 */
@Entity
@Table(name = "USER_ACCOUNT")
@Data
@Builder
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.Login.class)
    private String firstName;

    @JsonView(Views.Login.class)
    private String lastName;

    @JsonView(Views.Login.class)
    private String username;

    @JsonView(Views.Login.class)
    private String email;

    @Column(length = 60)
    @JsonView(Views.User.class)
    private String password;

    @JsonView(Views.User.class)
    private boolean enabled;

    @JsonView(Views.User.class)
    private boolean isUsing2FA;

    @JsonView(Views.User.class)
    private String secret;

    @JsonView(Views.User.class)
    private Date expiryDate;

    @JsonView(Views.User.class)
    private String birthDay;

    @JsonView(Views.User.class)
    private String phoneNumber;

    @Transient
    @JsonView(Views.Login.class)
    private String token;
    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonView(Views.User.class)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User() {
        super();
        this.secret = Base32.random();
        this.enabled = false;
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        if (!getEmail().equals(user.getEmail())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=")
                .append(id)
                .append(", firstName=").append(firstName)
                .append(", lastName=").append(lastName)
                .append(", email=").append(email)
                .append(", enabled=").append(enabled)
                .append(", username=").append(username)
                .append(", isUsing2FA=").append(isUsing2FA)
                .append(", secret=").append(secret)
                .append(", roles=").append(roles)
                .append("]");
        return builder.toString();
    }

}
