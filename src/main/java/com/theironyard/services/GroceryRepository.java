package com.theironyard.services;

import com.theironyard.entities.Grocery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by jessicahuffstutler on 11/12/15.
 */
public interface GroceryRepository extends PagingAndSortingRepository<Grocery, Integer> {
    Page<Grocery> findByCategoryOrderByNameAsc(Pageable pageable, String category);

    @Query("SELECT g FROM Grocery g WHERE LOWER(name) LIKE '%' || LOWER(?) || '%'")
    Page<Grocery> searchByName(Pageable pageable, String name);
}
