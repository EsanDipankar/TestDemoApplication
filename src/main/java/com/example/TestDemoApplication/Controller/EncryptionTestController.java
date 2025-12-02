package com.example.TestDemoApplication.Controller;

import com.example.TestDemoApplication.Config.AESUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class EncryptionTestController {

    @PostMapping("/encrypt")
    public Map<String, String> encrypt(@RequestBody Map<String, String> body) throws Exception {
        Map<String, String> encrypted = new HashMap<>();
        for (String key : body.keySet()) {
            encrypted.put(key, AESUtil.encrypt(body.get(key)));
        }
        return encrypted;
    }
}

