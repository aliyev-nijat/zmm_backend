package az.aliyev_nijat.zmm.controller;

import az.aliyev_nijat.zmm.model.entity.SettingsEntity;
import az.aliyev_nijat.zmm.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingService service;

    @GetMapping
    public ResponseEntity<SettingsEntity> getSettings() {
        return ResponseEntity.ok(service.getSettings());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<SettingsEntity> init() {
        return ResponseEntity.ok(service.init());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SettingsEntity> update(
            @RequestBody SettingsEntity settings
    ) {
        return ResponseEntity.ok(service.update(settings));
    }
}
