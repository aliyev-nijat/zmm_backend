package az.aliyev_nijat.zmm.controller;

import az.aliyev_nijat.zmm.model.entity.SliderEntity;
import az.aliyev_nijat.zmm.service.SliderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/slider")
public class SliderController {

    private final SliderService service;

    @GetMapping
    public ResponseEntity<List<SliderEntity>> getAll() {
        return ResponseEntity.ok(service.getSlider());
    }

    @PostMapping("/{sliderId}")
    public ResponseEntity<Map<String, Object>> uploadImage(
            @PathVariable Long sliderId,
            @RequestParam MultipartFile image
    ) {
        service.upload(sliderId, image);

        return ResponseEntity
                .noContent()
                .build();
    }

    /*@GetMapping("/init")
    public ResponseEntity<Void> init() {
        service.init();

        return ResponseEntity.noContent().build();
    }*/
}
