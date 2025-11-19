package az.aliyev_nijat.zmm.controller;

import az.aliyev_nijat.zmm.exception.EventNotFoundException;
import az.aliyev_nijat.zmm.model.dto.EventDto;
import az.aliyev_nijat.zmm.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<List<EventDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EventDto> create(
            @Valid @RequestBody EventDto event
    ) {
        EventDto newEvent = service.create(event);
        return newEvent == null ?
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build() :
                ResponseEntity
                        .ok(newEvent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(
            @PathVariable Long id,
            @Valid @RequestBody EventDto event
    ) throws EventNotFoundException {
        return ResponseEntity.ok(service.update(id, event));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{eventId}/image")
    public ResponseEntity<Map<String, Object>> uploadImage(
            @PathVariable Long eventId,
            @RequestParam MultipartFile image
    ) {
        return ResponseEntity.ok(service.uploadImage(eventId, image));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{eventId}/image")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long eventId
    ) {
        service.deleteImage(eventId);

        return ResponseEntity.noContent().build();
    }
}
