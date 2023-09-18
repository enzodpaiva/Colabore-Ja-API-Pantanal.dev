package pantanal.dev.colaboreja.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/social")
public class SocialActionController {
    
    @GetMapping
    public String getHello() {
        return "hello world";
    }
}
