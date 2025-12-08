package com.example.TestDemoApplication.Controller.Product;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.DTO.product.ProductDTO;
import com.example.TestDemoApplication.Entity.Product;
import com.example.TestDemoApplication.Entity.ProductResponseDTO;
import com.example.TestDemoApplication.Service.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    public ProductService productService;

    @RequestMapping("/addProduct")
    public String addProduct(@RequestBody ProductDTO pdto){
        String add= productService.dataInsert(pdto);
        return add;
    }

    @RequestMapping("/updateProduct")
    public String updateProduct(@RequestBody ProductDTO pdto){
        String updatep= productService.dataUpdate(pdto);
        return updatep;
    }

    @GetMapping("/getProductById")
    public ProductDTO getProductById(@RequestParam String productId) {
        return productService.getProductById(productId);
    }

    @DeleteMapping("/deleteProductById")
    public String deleteProductById(@RequestParam String productId){
        return productService.deleteProduct(productId);
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam String query){
        return productService.searchProducts(query);
    }

    @GetMapping("/getAllProduct")
    public List<ProductResponseDTO> getAllProduct(){
        return productService.getProduct();
    }

    @GetMapping("/getProductByCategory")
    public ResponseEntity<?> getproductBycategory(@RequestParam String encryptProductCategory){
        try{
            String productCategory = AESUtil.decrypt(encryptProductCategory);
            List<ProductResponseDTO> result =productService.getProductUsingCategory(productCategory);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return  ResponseEntity.badRequest().body("Invalid Product Category");
        }
    }

    @GetMapping("/filterProductByPrice")
    public  ResponseEntity<?> getProductByPrice(@RequestParam Long productPrice){
        try{
            Long price= Long.valueOf(AESUtil.decrypt(String.valueOf(productPrice)));
            List<ProductResponseDTO> result= productService.filterProductByPrice(price);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Invalid Price");
        }

    }
}
