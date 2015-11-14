package com.theironyard;

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

    String username;
    String password;
}
