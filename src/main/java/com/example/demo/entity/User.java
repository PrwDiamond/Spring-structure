package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "m_user")
public class User extends BaseEnity {

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 120)
    private String password;

    @Column(nullable = false, length = 120)
    private String name;

    //orphanRemoval = true --> Remove Parent and Child
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Social social;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Address> addresses;

    private String token;

    private Date tokenExpire;

    private boolean activated;
}
