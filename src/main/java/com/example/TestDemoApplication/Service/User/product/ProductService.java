package com.example.TestDemoApplication.Service.User.product;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.DTO.product.ProductDTO;
import com.example.TestDemoApplication.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductService {

    @Autowired
    private ProductDTO productDTO;

    @Autowired
    private Product product;


    public String dataInsert(ProductDTO dto){
    try{
        String productName =AESUtil.decrypt(dto.getProductName());

    }catch (Exception e){

    }
    return "1";
    }

}
