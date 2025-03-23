package com.example.Fashionecomerce.repository;

import com.example.Fashionecomerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByNameAndBrand(String name, String brand);

    List<Product> findByCategoryAndBrand(String category, String brand);

    List<Product> findByCategoryName(String category);

    List<Product> findByNameAndBrand(String brand, String name);

    List<Product> findByBrand(String brand);

    List<Product> findByName(String name);

    List<Product> findAllByCategoryId(Long categoryId);
}
