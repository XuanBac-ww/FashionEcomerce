package com.example.Fashionecomerce.repository;

import com.example.Fashionecomerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByCategoryName(String category);


    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByName(String name);

    List<Product> findByBrand(String brand);

    boolean existsByNameAndBrand(String name, String brand);

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findByBrandAndName(String brand, String name);
}
