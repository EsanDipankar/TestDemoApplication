package com.example.TestDemoApplication.Controller.Image;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.Service.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/productImage")
public class ImageController {
    @Autowired
    public ImageService imageService;

    @PostMapping("/upload-multiple")
    public List<String> uploadMultipleImages(@RequestParam("productId") String productId,
                                             @RequestParam("files") List<MultipartFile> files) {
        return imageService.uploadMultipleImages(productId, files);
    }
    @GetMapping("/get-images")
    public List<String> getImages(@RequestParam("productId") String encryptedProductId) {
        return imageService.getImages(encryptedProductId);
    }
    @DeleteMapping("/delete-image")
    public String deleteImage(@RequestParam("productId") String encryptedProductId){
        try{
            String productId= AESUtil.decrypt(encryptedProductId);
            String result= imageService.deleteImagesByProductId(productId);
            return result;

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
