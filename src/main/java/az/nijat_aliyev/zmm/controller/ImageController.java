package az.nijat_aliyev.zmm.controller;

import az.nijat_aliyev.zmm.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;

    @PostMapping(value = "/{dir}/{name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> upload(
            @PathVariable String dir,
            @PathVariable String name,
            @RequestParam MultipartFile file
    ) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("url", service.upload(dir, name, file));

        return ResponseEntity.ok(response);
    }
}
