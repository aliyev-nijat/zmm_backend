package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.model.entity.SettingsEntity;
import az.aliyev_nijat.zmm.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingsRepository repository;

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
                repository.findById(1L).orElseThrow().getImageUrl()
        );
        return repository.save(settings);
    }
}
