package az.nijat_aliyev.zmm.controller;

import az.nijat_aliyev.zmm.model.Event;
import az.nijat_aliyev.zmm.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<Event>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Event> create(
            @RequestBody Event event
    ) {
        Event newEvent = service.create(event);
        return newEvent == null ?
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build() :
                ResponseEntity
                        .ok(newEvent);
    }
}
