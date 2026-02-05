package az.aliyev_nijat.zmm.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");

        if (!requestUri.startsWith("/api") && statusCode != null && statusCode == 404) {
            return requestUri.startsWith("/adminpanel") ? "redirect:/adminpanel" : "redirect:/";
        }

        return "/error.html";
    }
}