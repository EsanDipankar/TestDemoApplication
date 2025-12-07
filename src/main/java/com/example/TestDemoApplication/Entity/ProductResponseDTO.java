package com.example.TestDemoApplication.Entity;

import com.example.TestDemoApplication.DTO.Image.ImageDTO;
import com.example.TestDemoApplication.DTO.product.ProductDTO;

import java.util.List;

public class ProductResponseDTO {
    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    private ProductDTO product;
    private List<ImageDTO> images;
}
