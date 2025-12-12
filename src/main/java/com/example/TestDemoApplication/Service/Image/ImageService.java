package com.example.TestDemoApplication.Service.Image;

import com.example.TestDemoApplication.Config.AESUtil;
import com.example.TestDemoApplication.Entity.ImageEntity;
import com.example.TestDemoApplication.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    public ImageRepository imageRepository;

    private static final String UPLOAD_DIR = "uploads/products/";

    public List<String> uploadMultipleImages(String encryptedProductId,
                                             List<MultipartFile> files) {

        List<String> encryptedFileNames = new ArrayList<>();

        try {
            String productId = AESUtil.decrypt(encryptedProductId);

            // Create product directory if not exists
            File dir = new File(UPLOAD_DIR + productId);
            if (!dir.exists()) dir.mkdirs();

            int order = 0;

            for (MultipartFile file : files) {

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                // Save file to server folder
                File savedFile = new File(dir, fileName);
                file.transferTo(savedFile);

                // Save into DB
                ImageEntity image = new ImageEntity();
                image.setProductId(productId);
                image.setImageName(fileName);
                image.setSortOrder(order++);
                imageRepository.save(image);

                // Encrypt fileName for frontend
                encryptedFileNames.add(AESUtil.encrypt(fileName));
            }

            return encryptedFileNames;

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload images: " + e.getMessage());
        }
    }

    public List<String> getImages(String encryptedProductId) {
        try {
            String productId = AESUtil.decrypt(encryptedProductId);

            List<ImageEntity> images = imageRepository.findByProductId(productId);

            List<String> imageUrls = new ArrayList<>();

            for (ImageEntity image : images) {
                String fileName = image.getImageName();

                // Create URL for frontend
                String imageUrl = "http://localhost:8082/uploads/products/" + productId + "/" + fileName;

                imageUrls.add(imageUrl);
            }
            return imageUrls;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch images: " + e.getMessage());
        }
    }
    public String deleteImagesByProductId(String productId){

        return "";

    }

}

