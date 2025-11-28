package Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaTest {
    @GetMapping("testApi")
    public void testApi(){
        System.out.print("Hello Test API");
    }
}
