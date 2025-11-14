package az.aliyev_nijat.zmm.controller;

import az.aliyev_nijat.zmm.model.dto.CourseDto;
import az.aliyev_nijat.zmm.model.dto.LoginDto;
import az.aliyev_nijat.zmm.model.entity.UserEntity;
import az.aliyev_nijat.zmm.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService service;

    //@GetMapping("/create/root/{password}")
    public ResponseEntity<Void> createRoot(
            @PathVariable String password
    ) {
        service.createRoot(password);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROOT')")
    @PostMapping("/createadmin/{username}")
    public ResponseEntity<Map<String, Object>> createAdmin(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(service.createAdmin(username));
    }

    @PostMapping("/unlock/login")
    public ResponseEntity<Void> unlockAdminUser(
            @RequestBody Map<String, String> body
    ) {
        service.unlockAdminUser(
                body.get("username"),
                body.get("currentPassword"),
                body.get("newPassword")
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginDto body
    ) {
        Map<String,Object> user = service.login(body);
        if (user == null) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("message", "bad credentials");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(errorMessage);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntity> getCurrentUser() {
        return ResponseEntity.ok(service.getCurrentUser());
    }
}
