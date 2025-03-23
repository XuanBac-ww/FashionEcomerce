package com.example.Fashionecomerce.dtos;

import com.example.Fashionecomerce.model.Category;
import com.example.Fashionecomerce.model.Image;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
