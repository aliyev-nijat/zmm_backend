package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.model.entity.ImageEntity;
import az.aliyev_nijat.zmm.model.entity.ImageExtension;
import az.aliyev_nijat.zmm.model.entity.SettingsEntity;
import az.aliyev_nijat.zmm.repository.ImageRepository;
import az.aliyev_nijat.zmm.repository.SettingsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingsRepository repository;
    private final ImageRepository imageRepository;

    public SettingsEntity init() {
        SettingsEntity settings = new SettingsEntity();
        settings.setId(1L);

        return repository.save(settings);
    }

    public SettingsEntity getSettings() {
        return repository.findById(1L).orElseThrow();
    }

    public SettingsEntity update(SettingsEntity settings) {
        settings.setId(1L);
        settings.setImageUrl(
                getSettings().getImageUrl()
        );
        return repository.save(settings);
    }

    public Map<String, Object> uploadImage(
            MultipartFile image
    ) {
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        SettingsEntity settings = getSettings();
        if (settings.getImageUrl() != null) {
            Long oldImageId = Long.valueOf(
                    settings.getImageUrl()
                    .substring("/api/images/".length())
            );
            imageRepository.delete(oldImageId);
            settings.setImageUrl(null);
        }
        String[] splited = image.getOriginalFilename().split("\\.");
        ImageExtension extension = ImageExtension
                .valueOf(
                        splited[splited.length - 1].toUpperCase()
                );
        byte[] content;
        try {
            content = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setExtension(extension);
        imageEntity.setContent(content);
        ImageEntity newImageEntity = imageRepository.create(imageEntity);
        Long newImageId = newImageEntity.getId();
        settings.setImageUrl(
                String.format("/api/images/%d",newImageId)
        );
        repository.save(settings);
        Map<String, Object> result = new HashMap<>();
        result.put("imageId", newImageId);
        result.put("imageUrl", String.format("/images/%d", newImageId));

        return result;
    }
}
