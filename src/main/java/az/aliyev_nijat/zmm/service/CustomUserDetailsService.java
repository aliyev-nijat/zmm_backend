package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.model.CustomUserDetails;
import az.aliyev_nijat.zmm.model.dto.CourseDto;
import az.aliyev_nijat.zmm.model.dto.LoginDto;
import az.aliyev_nijat.zmm.model.entity.UserEntity;
import az.aliyev_nijat.zmm.repository.UserRepository;
import az.aliyev_nijat.zmm.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    private final JwtUtil jwtUtil;
    private static final List<UserEntity> NEW_USERS = new LinkedList<>();

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        List<UserEntity> users = repository.findByUsername(username);
        if (users.size() == 1) {

            return new CustomUserDetails(users.getFirst());
        }
        if (users.size() > 1) {
            log.error("Same usernames for different users.{}", users);
        }

        return null;
    }

    public Map<String, Object> login(LoginDto body) {
        List<UserEntity> users = repository.findByUsername(body.getUsername());
        if (
                users.size() == 1 &&
                        users.getFirst()
                                .getPasswordHash()
                                .equals(
                                        hash(body.getPassword())
                                )
        ) {
            Map<String, Object> response = new HashMap<>();
            response.put("token type", "JWT");
            response.put("token", jwtUtil.generateToken(body.getUsername()));
            response.put("role", users.getFirst().getRole().substring(5));

            return response;
        }
        if (users.size() > 1) {
            log.error("Same usernames for different users.{}", users);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        return null;
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        String username = principal instanceof UserDetails ?
                ((UserDetails) principal).getUsername() :
                principal.toString();
        List<UserEntity> users = repository.findByUsername(username);
        if (users.size() == 1) {
            UserEntity user = users.getFirst();
            user.setId(null);
            user.setRole(user.getRole().substring(5));
            user.setPasswordHash(null);
            return users.getFirst();
        }
        if (users.size() > 1) {
            log.error("Same usernames for different users.{}", users);
            throw new RuntimeException("Same usernames for different users.");
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public void createRoot(String password) {
        UserEntity user = new UserEntity();
        user.setUsername("root");
        user.setPasswordHash(hash(password));
        user.setRole("ROLE_ROOT");
        repository.save(user);
    }

    public Map<String, Object> createAdmin(@NonNull String username) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        String generatedPassword = generatePassword();
        user.setPasswordHash(hash(generatedPassword));
        user.setRole("ROLE_ADMIN");
        if (!repository.findByUsername(username).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "username already exists"
            );
        }
        NEW_USERS.add(user);
        //repository.save(user);
        HashMap<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("password", generatedPassword);

        return response;
    }

    public void unlockAdminUser(
            String username,
            String currentPassword,
            String newPassword
    ) {
        for (UserEntity user : NEW_USERS) {
            if (
                    user
                            .getUsername()
                            .equals(username) &&
                            user
                                    .getRole()
                                    .equals("ROLE_ADMIN") &&
                            user
                                    .getPasswordHash()
                                    .equals(hash(currentPassword))
            ) {

            }
        }
        UserEntity userFromNewUsers = NEW_USERS.stream()
                .filter(user -> user
                        .getUsername()
                        .equals(username) &&
                        user
                                .getRole()
                                .equals("ROLE_ADMIN") &&
                        user
                                .getPasswordHash()
                                .equals(hash(currentPassword)))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        userFromNewUsers.setPasswordHash(hash(newPassword));
        repository.save(userFromNewUsers);
        if (!NEW_USERS.remove(userFromNewUsers)) {
            log.warn("User unlocked but not removed from locked list");
        }
    }

    private String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    private static String generatePassword() {
        String chars =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }
}
