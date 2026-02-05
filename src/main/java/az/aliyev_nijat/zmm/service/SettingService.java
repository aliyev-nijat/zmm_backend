package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.model.entity.SettingsEntity;
import az.aliyev_nijat.zmm.repository.SettingsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingsRepository repository;
    private final ImageService imageService;

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

    @Transactional
    public Map<String, Object> uploadImage(
            MultipartFile image
    ) {
        SettingsEntity settings = getSettings();
        if (settings.getImageUrl() != null) {
            Long oldImageId = Long.valueOf(
                    settings.getImageUrl()
                    .substring("/api/images/".length())
            );
            imageService.deleteById(oldImageId);
            settings.setImageUrl(null);
        }
        Long newImageId = imageService.create(image);
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
