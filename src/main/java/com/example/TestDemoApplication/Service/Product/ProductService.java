package com.example.TestDemoApplication.Service.Product;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.DTO.Image.ImageDTO;
import com.example.TestDemoApplication.DTO.product.ProductDTO;
import com.example.TestDemoApplication.Entity.ImageEntity;
import com.example.TestDemoApplication.Entity.Product;
import com.example.TestDemoApplication.Entity.ProductResponseDTO;
import com.example.TestDemoApplication.Repository.ImageRepository;
import com.example.TestDemoApplication.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public ImageRepository imageRepository;
    @Autowired
    public ProductResponseDTO productResponseDTO;

    // ---------------------------------------------------------
    // INSERT PRODUCT
    // ---------------------------------------------------------
    public String dataInsert(ProductDTO dto) {
        try {
            Product product = new Product();

            // ID (String)
            product.setId(AESUtil.decrypt(dto.getId()));

            // Strings
            product.setProductName(AESUtil.decrypt(dto.getProductName()));
            product.setCategory(AESUtil.decrypt(dto.getCategory()));
            product.setSubCategory(AESUtil.decrypt(dto.getSubCategory()));
            product.setSellerName(AESUtil.decrypt(dto.getSellerName()));
            product.setSellerId(AESUtil.decrypt(dto.getSellerId()));
            product.setModelNumber(AESUtil.decrypt(dto.getModelNumber()));
            product.setModelName(AESUtil.decrypt(dto.getModelName()));
            product.setType(AESUtil.decrypt(dto.getType()));
            product.setColor(AESUtil.decrypt(dto.getColor()));

            // Numbers (decrypt → parseLong)
            product.setPrice(Long.parseLong(AESUtil.decrypt(dto.getPrice())));
            product.setWidth(Long.parseLong(AESUtil.decrypt(dto.getWidth())));
            product.setHeight(Long.parseLong(AESUtil.decrypt(dto.getHeight())));
            product.setDepth(Long.parseLong(AESUtil.decrypt(dto.getDepth())));
            product.setRating(Long.parseLong(AESUtil.decrypt(dto.getRating())));
            product.setDiscount(Long.parseLong(AESUtil.decrypt(dto.getDiscount())));
            product.setQuantity(Long.parseLong(AESUtil.decrypt(dto.getQuantity())));

            productRepository.save(product);
            return "Product added successfully";

        } catch (Exception e) {
            return "Invalid Product details: " + e.getMessage();
        }
    }

    // ---------------------------------------------------------
    // UPDATE PRODUCT (Partial Update Allowed)
    // ---------------------------------------------------------
    public String dataUpdate(ProductDTO dto) {
        try {
            String id = AESUtil.decrypt(dto.getId());

            Optional<Product> productOpt = productRepository.findById(id);

            if (productOpt.isEmpty()) {
                return "Product not found";
            }

            Product product = productOpt.get();

            // Update only non-null fields

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

    // ---------------------------------------------------------
    // GET PRODUCT BY ID (returns encrypted DTO)
    // ---------------------------------------------------------
    public ProductDTO getProductById(String encryptedProductId) {
        try {
            String id = AESUtil.decrypt(encryptedProductId);

            Optional<Product> productOpt = productRepository.findById(id);

            if (productOpt.isEmpty()) {
                return null;
            }

            Product product = productOpt.get();
            ProductDTO dto = new ProductDTO();

            // Encrypt before sending
            dto.setId(AESUtil.encrypt(product.getId()));
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
            throw new RuntimeException("Error fetching product: " + e.getMessage());
        }
    }
    public String deleteProduct(String productId) {
        try {
            String id = AESUtil.decrypt(productId);
            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                return "Product already deleted";
            }
            productRepository.deleteById(id);
            return "Product deleted Successfully";

        } catch (Exception e) {
            return "Invalid product id" + e.getMessage();
        }
    }
    private ProductDTO convertToEncryptedDTO(Product p) {
        ProductDTO dto = new ProductDTO();
        try{
            dto.setId(AESUtil.encrypt(String.valueOf(p.getId())));
            dto.setProductName(AESUtil.encrypt(p.getProductName()));
            dto.setCategory(AESUtil.encrypt(p.getCategory()));
            dto.setSubCategory(AESUtil.encrypt(p.getSubCategory()));
            dto.setPrice(AESUtil.encrypt(String.valueOf(p.getPrice())));
            dto.setSellerName(AESUtil.encrypt(p.getSellerName()));
            dto.setSellerId(AESUtil.encrypt(p.getSellerId()));
            dto.setModelNumber(AESUtil.encrypt(p.getModelNumber()));
            dto.setModelName(AESUtil.encrypt(p.getModelName()));
            dto.setType(AESUtil.encrypt(p.getType()));
            dto.setColor(AESUtil.encrypt(p.getColor()));
            dto.setWidth(AESUtil.encrypt(String.valueOf(p.getWidth())));
            dto.setHeight(AESUtil.encrypt(String.valueOf(p.getHeight())));
            dto.setDepth(AESUtil.encrypt(String.valueOf(p.getDepth())));
            dto.setRating(AESUtil.encrypt(String.valueOf(p.getRating())));
            dto.setDiscount(AESUtil.encrypt(String.valueOf(p.getDiscount())));
            dto.setQuantity(AESUtil.encrypt(String.valueOf(p.getQuantity())));
            return dto;
        }catch(Exception e){
            return  dto;
        }
    }

    public List<ProductDTO> searchProducts(String keyword){
        List<Product> productList= new ArrayList<>();
        productList.addAll(productRepository.findByProductNameContainingIgnoreCase(keyword));
        productList.addAll(productRepository.findByCategoryContainingIgnoreCase(keyword));
        productList.addAll(productRepository.findBySubCategoryContainingIgnoreCase(keyword));
        productList=productList.stream().distinct().toList();

        return productList.stream().map(product-> convertToEncryptedDTO(product)).toList();
    }
    public List<ProductResponseDTO>  getProduct(){
        List<Product> productList= productRepository.findAll();
        List<ProductResponseDTO> result= new ArrayList<>();
        for(Product product: productList){
            ProductResponseDTO response = new ProductResponseDTO();
            ProductDTO dto = convertToProductDTO(product);

            List<ImageEntity> images = imageRepository.findByProductId(product.getId());
            List<ImageDTO> imageDTOs = images.stream()
                    .map(img -> new ImageDTO(img.getId(), img.getProductId(), img.getImageName(), img.getSortOrder()))
                    .collect(Collectors.toList());
            response.setProduct(dto);
            response.setImages(imageDTOs);
            result.add(response);
        }
        return result;
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setCategory(product.getCategory());
        dto.setSubCategory(product.getSubCategory());
        dto.setPrice(String.valueOf(product.getPrice()));
        dto.setSellerName(product.getSellerName());
        dto.setSellerId(product.getSellerId());
        dto.setModelNumber(product.getModelNumber());
        dto.setModelName(product.getModelName());
        dto.setType(product.getType());
        dto.setColor(product.getColor());
        dto.setWidth(String.valueOf(product.getWidth()));
        dto.setHeight(String.valueOf(product.getHeight()));
        dto.setDepth(String.valueOf(product.getDepth()));
        dto.setQuantity(String.valueOf(product.getQuantity()));
        dto.setDiscount(String.valueOf(product.getDiscount()));
        dto.setRating(String.valueOf(product.getRating()));
        return  dto;
    }

}
