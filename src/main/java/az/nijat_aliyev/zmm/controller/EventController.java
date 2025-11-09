package az.nijat_aliyev.zmm.controller;

import az.nijat_aliyev.zmm.model.dto.EventDto;
import az.nijat_aliyev.zmm.model.entity.EventEntity;
import az.nijat_aliyev.zmm.service.EventService;
import az.nijat_aliyev.zmm.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<List<EventEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<EventDto> create(
            @Validated(OnCreate.class) @RequestBody EventDto event
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
}
