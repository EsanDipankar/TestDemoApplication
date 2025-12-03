package com.example.TestDemoApplication.Service.User.product;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.DTO.product.ProductDTO;
import com.example.TestDemoApplication.Entity.Product;
import com.example.TestDemoApplication.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    // --------------------- INSERT --------------------------
    public String dataInsert(ProductDTO dto) {
        try {
            String id = AESUtil.decrypt(dto.getId());
            Long price = Long.parseLong(AESUtil.decrypt(dto.getPrice()));
            Long width = Long.parseLong(AESUtil.decrypt(dto.getWidth()));
            Long height = Long.parseLong(AESUtil.decrypt(dto.getHeight()));
            Long depth = Long.parseLong(AESUtil.decrypt(dto.getDepth()));
            Long rating = Long.parseLong(AESUtil.decrypt(dto.getRating()));
            Long discount = Long.parseLong(AESUtil.decrypt(dto.getDiscount()));
            Long quantity = Long.parseLong(AESUtil.decrypt(dto.getQuantity()));

            Product product = new Product();
            product.setId(id);
            product.setProductName(AESUtil.decrypt(dto.getProductName()));
            product.setCategory(AESUtil.decrypt(dto.getCategory()));
            product.setSubCategory(AESUtil.decrypt(dto.getSubCategory()));
            product.setPrice(price);
            product.setSellerName(AESUtil.decrypt(dto.getSellerName()));
            product.setSellerId(AESUtil.decrypt(dto.getSellerId()));
            product.setModelNumber(AESUtil.decrypt(dto.getModelNumber()));
            product.setModelName(AESUtil.decrypt(dto.getModelName()));
            product.setType(AESUtil.decrypt(dto.getType()));
            product.setColor(AESUtil.decrypt(dto.getColor()));
            product.setWidth(width);
            product.setHeight(height);
            product.setDepth(depth);
            product.setRating(rating);
            product.setDiscount(discount);
            product.setQuantity(quantity);

            productRepository.save(product);
            return "Product added successfully";

        } catch (Exception e) {
            return "Invalid Product details: " + e.getMessage();
        }
    }

    // --------------------- UPDATE --------------------------
    public String dataUpdate(ProductDTO dto) {
        try {
            Long id = Long.parseLong(AESUtil.decrypt(dto.getId()));
            Product product = productRepository.findById(id);

            if (product == null) {
                return "Product not found";
            }

            if (dto.getProductName() != null)
                product.setProductName(AESUtil.decrypt(dto.getProductName()));

            if (dto.getCategory() != null)
                product.setCategory(AESUtil.decrypt(dto.getCategory()));

            if (dto.getSubCategory() != null)
                product.setSubCategory(AESUtil.decrypt(dto.getSubCategory()));

            if (dto.getPrice() != null)
                product.setPrice(Long.parseLong(AESUtil.decrypt(dto.getPrice())));

            if (dto.getSellerName() != null)
                product.setSellerName(AESUtil.decrypt(dto.getSellerName()));

            if (dto.getSellerId() != null)
                product.setSellerId(AESUtil.decrypt(dto.getSellerId()));

            if (dto.getModelNumber() != null)
                product.setModelNumber(AESUtil.decrypt(dto.getModelNumber()));

            if (dto.getModelName() != null)
                product.setModelName(AESUtil.decrypt(dto.getModelName()));

            if (dto.getType() != null)
                product.setType(AESUtil.decrypt(dto.getType()));

            if (dto.getColor() != null)
                product.setColor(AESUtil.decrypt(dto.getColor()));

            if (dto.getWidth() != null)
                product.setWidth(Long.parseLong(AESUtil.decrypt(dto.getWidth())));

            if (dto.getHeight() != null)
                product.setHeight(Long.parseLong(AESUtil.decrypt(dto.getHeight())));

            if (dto.getDepth() != null)
                product.setDepth(Long.parseLong(AESUtil.decrypt(dto.getDepth())));

            if (dto.getRating() != null)
                product.setRating(Long.parseLong(AESUtil.decrypt(dto.getRating())));

            if (dto.getDiscount() != null)
                product.setDiscount(Long.parseLong(AESUtil.decrypt(dto.getDiscount())));

            if (dto.getQuantity() != null)
                product.setQuantity(Long.parseLong(AESUtil.decrypt(dto.getQuantity())));

            productRepository.save(product);
            return "Product updated successfully";
        } catch (Exception e) {
            return "Invalid product update details: " + e.getMessage();
        }
    }

    public ProductDTO getProductById(Long productId) {
        try {
            Product product = productRepository.findById(productId);

            if (product == null) {
                return null;  // or throw exception
            }

            ProductDTO dto = new ProductDTO();

            // Encrypt all fields before returning
            dto.setId(AESUtil.encrypt(String.valueOf(product.getId())));
            dto.setProductName(AESUtil.encrypt(product.getProductName()));
            dto.setCategory(AESUtil.encrypt(product.getCategory()));
            dto.setSubCategory(AESUtil.encrypt(product.getSubCategory()));
            dto.setPrice(AESUtil.encrypt(String.valueOf(product.getPrice())));
            dto.setSellerName(AESUtil.encrypt(product.getSellerName()));
            dto.setSellerId(AESUtil.encrypt(product.getSellerId()));
            dto.setModelNumber(AESUtil.encrypt(product.getModelNumber()));
            dto.setModelName(AESUtil.encrypt(product.getModelName()));
            dto.setType(AESUtil.encrypt(product.getType()));
            dto.setColor(AESUtil.encrypt(product.getColor()));
            dto.setWidth(AESUtil.encrypt(String.valueOf(product.getWidth())));
            dto.setHeight(AESUtil.encrypt(String.valueOf(product.getHeight())));
            dto.setDepth(AESUtil.encrypt(String.valueOf(product.getDepth())));
            dto.setRating(AESUtil.encrypt(String.valueOf(product.getRating())));
            dto.setDiscount(AESUtil.encrypt(String.valueOf(product.getDiscount())));
            dto.setQuantity(AESUtil.encrypt(String.valueOf(product.getQuantity())));

            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting product data: " + e.getMessage());
        }
    }
    public String deleteProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            return "Product not found";
        }
        productRepository.deleteById(productId);
        return "Product Deleted";
    }
}
