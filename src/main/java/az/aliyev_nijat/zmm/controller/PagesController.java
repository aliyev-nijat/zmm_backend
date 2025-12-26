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

    @GetMapping("/adminpanel")
    public String adminpanel(){
        return "/adminpanel/index.html";
    }

    @GetMapping("/adminpanel/courses")
    public String adminpanelCourses(){
        return "/adminpanel/courses.html";
    }

    @GetMapping("/adminpanel/coursesUpdate")
    public String adminpanelCoursesUpdate(){
        return "/adminpanel/coursesUpdate.html";
    }

    @GetMapping("/adminpanel/events")
    public String adminpanelEvents(){
        return "/adminpanel/events.html";
    }

    @GetMapping("/adminpanel/login")
    public String adminpanelLogin(){
        return "/adminpanel/login.html";
    }

    @GetMapping("/adminpanel/settings")
    public String adminpanelSettings(){
        return "/adminpanel/settings.html";
    }

    @GetMapping("/adminpanel/slider")
    public String adminpanelSlider(){
        return "/adminpanel/slider.html";
    }

    @GetMapping("/adminpanel/unlockAdmin")
    public String adminpanelUnlockAdmin(){
        return "/adminpanel/unlockAdmin.html";
    }
}
