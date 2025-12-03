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

    public String dataInsert(ProductDTO dto){
        try{
            Long Id= Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getId())));
            String productName= AESUtil.decrypt(dto.getProductName());
            String category= AESUtil.decrypt(dto.getCategory());
            String subCategory= AESUtil.decrypt(dto.getSubCategory());
            Long price= Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getPrice())));
            String sellerName= AESUtil.decrypt(dto.getSellerName());
            String sellerId=AESUtil.decrypt(dto.getSellerId());
            String modelNumber=AESUtil.decrypt(dto.getModelNumber());
            String modelName= AESUtil.decrypt(dto.getModelName());
            String type= AESUtil.decrypt(dto.getType());
            String color= AESUtil.decrypt(dto.getColor());
            Long width=Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getWidth())));
            Long height=Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getHeight())));
            Long depth= Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getDepth())));
            Long rating=Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getRating())));
            Long discount=Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getDiscount())));
            Long quantity=Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getQuantity())));
            Product productEntity= new Product();
            productEntity.setId(Id);
            productEntity.setProductName(productName);
            productEntity.setCategory(category);
            productEntity.setSubCategory(subCategory);
            productEntity.setPrice(price);
            productEntity.setSellerId(sellerId);
            productEntity.setSellerName(sellerName);
            productEntity.setModelName(modelName);
            productEntity.setModelNumber(modelNumber);
            productEntity.setType(type);
            productEntity.setColor(color);
            productEntity.setWidth(width);
            productEntity.setHeight(height);
            productEntity.setDepth(depth);
            productEntity.setRating(rating);
            productEntity.setDiscount(discount);
            productEntity.setQuantity(quantity);
            productRepository.save(productEntity);
            return "Product added successfully";
        }catch (Exception e){
            return "Invalid Product details"+ e.getMessage();
        }
    }

    public String dataUpdate(ProductDTO dto){
        try {
            Long id = Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getId())));
            Product product = productRepository.findById(id);
            if(product== null){
                return "Product Not found";
            }
            if (dto.getProductName() != null)
                product.setProductName(AESUtil.decrypt(dto.getProductName()));

            if (dto.getCategory() != null)
                product.setCategory(AESUtil.decrypt(dto.getCategory()));

            if (dto.getSubCategory() != null)
                product.setSubCategory(AESUtil.decrypt(dto.getSubCategory()));

            if (dto.getPrice() != null)
                product.setPrice(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getPrice()))));

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
                product.setWidth(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getWidth()))));

            if (dto.getHeight() != null)
                product.setHeight(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getHeight()))));

            if (dto.getDepth() != null)
                product.setDepth(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getDepth()))));

            if (dto.getRating() != null)
                product.setRating(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getRating()))));

            if (dto.getDiscount() != null)
                product.setDiscount(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getDiscount()))));

            if (dto.getQuantity() != null)
                product.setQuantity(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getQuantity()))));
            productRepository.save(product);
            return "Product Updated Successfully";
        }catch (Exception e){
            return "Invalid product update details: " + e.getMessage();
        }
    }

}
