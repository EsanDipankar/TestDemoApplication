package com.example.TestDemoApplication.Controller.Product;

import com.example.TestDemoApplication.DTO.product.ProductDTO;
import com.example.TestDemoApplication.Service.User.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String updateProduct(@RequestBody ProductDTO pdto){
        String updatep= productService.dataUpdate(pdto);
        return updatep;
    }
}
