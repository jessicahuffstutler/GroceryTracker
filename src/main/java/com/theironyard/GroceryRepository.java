package com.theironyard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jessicahuffstutler on 11/12/15.
 */
public interface GroceryRepository extends CrudRepository<Grocery, Integer> {
    List<Grocery> findByCategoryOrderByNameAsc(String category);

    @Query("SELECT g FROM Grocery g WHERE LOWER(name) LIKE '%' || LOWER(?) || '%'")
    List<Grocery> searchByName(String name);
}
