package com.theironyard.entities;

import com.theironyard.entities.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by jessicahuffstutler on 11/12/15.
 */
@Entity
public class Grocery {
    @Id
    @GeneratedValue
    Integer id;

    public String name;
    public String brand;
    public Double quantity;
    public String quantityType; //lbs, ounces, gallons, etc
    public String category; //meat, vegetables, fruit, etc

    @ManyToOne
    public User user;

}
