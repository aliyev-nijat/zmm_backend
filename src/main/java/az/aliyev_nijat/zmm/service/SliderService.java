package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.model.entity.SliderEntity;
import az.aliyev_nijat.zmm.repository.SliderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SliderService {

    private final SliderRepository repository;
    private final ImageService imageService;

    @Transactional
    public void upload(
            Long id,
            MultipartFile image
    ) {
        SliderEntity sliderEntity = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST)
                );
        Long oldImageId = sliderEntity.getImageId();
        if (oldImageId != null) {
            imageService.deleteById(oldImageId);
        }
        Long newImageId = imageService.create(image);
        sliderEntity.setImageId(newImageId);
        sliderEntity.setImageUrl(String.format(
                "/api/images/%d",
                newImageId
        ));
        repository.save(sliderEntity);
    }

    public List<SliderEntity> getSlider() {
        return repository
                .findAll()
                .stream()
                .toList();
    }

    /*public void init() {
        List<SliderEntity> sliderEntities = new LinkedList<>();
        sliderEntities.add(new SliderEntity());
        sliderEntities.add(new SliderEntity());
        sliderEntities.add(new SliderEntity());
        sliderEntities.add(new SliderEntity());
        sliderEntities.add(new SliderEntity());
        sliderEntities.add(new SliderEntity());
        sliderEntities.forEach(slide -> {
            slide.setImageUrl("");
            slide.setImageId(-1L);
            repository.save(slide);
        });
    }*/
}
