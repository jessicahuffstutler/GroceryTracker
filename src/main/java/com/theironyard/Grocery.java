package com.theironyard;

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

    String name;
    String brand;
    Double quantity;
    String quantityType; //lbs, ounces, gallons, etc
    String category; //meat, vegetables, fruit, etc

    @ManyToOne
    User user;

}
