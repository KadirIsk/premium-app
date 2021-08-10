package com.mogan.premiumapp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}

