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
    @Autowired
    private AESUtil aesUtil;

    public String dataInsert(ProductDTO dto){
    try{
        Product productentity= new Product();
        productentity.setProductName(AESUtil.decrypt(dto.getProductName()));
        productentity.setCategory(AESUtil.decrypt(dto.getCategory()));
        productentity.setPrice(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getPrice()))));
        productentity.setId(Long.parseLong(AESUtil.decrypt(String.valueOf(dto.getId()))));



    }catch (Exception e){

    }
    return "1";
    }

}
