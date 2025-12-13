package az.aliyev_nijat.zmm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

    @GetMapping("/about")
    public String about() {
        return "about.html";
    }

    @GetMapping("/apply")
    public String apply() {
        return "apply.html";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact.html";
    }

    @GetMapping("/courseDetail")
    public String courseDetail() {
        return "courseDetail.html";
    }

    @GetMapping("/courses")
    public String courses() {
        return "courses.html";
    }

    @GetMapping("/eventDetail")
    public String eventDetail() {
        return "eventDetail.html";
    }

    @GetMapping("/events")
    public String events() {
        return "events.html";
    }
}
