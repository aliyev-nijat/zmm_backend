package az.aliyev_nijat.zmm.controller;

import az.aliyev_nijat.zmm.model.entity.ImageResponse;
import az.aliyev_nijat.zmm.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getById(
            @PathVariable Long id
    ) {
        ImageResponse image = imageService.getImageById(id);

        return ResponseEntity.ok()
                .contentType(image.mimeType())
                .body(image.content());
    }
}
