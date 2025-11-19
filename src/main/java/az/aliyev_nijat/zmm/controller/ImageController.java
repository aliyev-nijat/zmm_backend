package az.aliyev_nijat.zmm.controller;

import az.aliyev_nijat.zmm.model.entity.ImageEntity;
import az.aliyev_nijat.zmm.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
        ImageEntity image = imageService.getImageById(id);
        String mimeType = String.format("image/%s", image.getExtension());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(image.getContent());
    }
}
