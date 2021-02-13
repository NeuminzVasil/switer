package com.example.servingwebcontent.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Login can`t be empty")
    @Column(name = "login")
    private String login;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @NotBlank(message = "password can`t be empty")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "password confirmation can`t be empty")
    @Transient
    private String password2;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Role> authorities;

    @Email(message = "email isn`t correct")
    @NotBlank(message = "mail can`t be empty")
    private String email;
    private String activationCode;

    public Customer(String login, String firstName, String lastName, Boolean active) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }

    public boolean isAdmin() {
        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @Override
    public String toString() {
        return "Customer{" +
                "login='" + login + '\'' +
                ", active=" + active +
                ", authorities=" + authorities +
                '}';
    }

    @Override
    public String getUsername() {
        return getLogin();
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
        return getActive();
    }
}
