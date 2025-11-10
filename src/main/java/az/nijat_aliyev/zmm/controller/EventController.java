package az.nijat_aliyev.zmm.controller;

import az.nijat_aliyev.zmm.exception.EventNotFoundException;
import az.nijat_aliyev.zmm.model.dto.EventDto;
import az.nijat_aliyev.zmm.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(
            @PathVariable Long id,
            @Valid @RequestBody EventDto event
    ) throws EventNotFoundException {
        return ResponseEntity.ok(service.update(id, event));
    }
}
