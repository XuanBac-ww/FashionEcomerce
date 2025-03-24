package com.example.Fashionecomerce.service.product;

import com.example.Fashionecomerce.dtos.ProductDto;
import com.example.Fashionecomerce.model.Product;
import com.example.Fashionecomerce.request.AddProductRequest;
import com.example.Fashionecomerce.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);

    Product updateProduct(ProductUpdateRequest request, Long productId);

    Product getProductById(Long productId);

    void deleteProductById(Long productId);


    List<Product> getAllProducts();

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrandAndName(String brand, String name);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByName(String name);

    List<Product> findDistinctProductsByName();

    List<String> getAllDistinctBrands();

    List<ProductDto> getConvertedProducts(List<Product> products);


    ProductDto convertToDto(Product product);

    List<Product> getProductsByCategoryId(Long categoryId);
}
