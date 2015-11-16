package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by jessicahuffstutler on 11/12/15.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    Integer id;

    public String username;
    public String password;
}
