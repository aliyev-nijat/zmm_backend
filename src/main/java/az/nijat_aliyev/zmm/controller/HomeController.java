package az.nijat_aliyev.zmm.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class HomeController {

    @GetMapping("/test")
    public String hello() {
        return "API works successfully!!!";
    }
}
