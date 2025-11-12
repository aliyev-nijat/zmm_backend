package az.nijat_aliyev.zmm.service;

import az.nijat_aliyev.zmm.model.entity.ImageEntity;
import az.nijat_aliyev.zmm.model.entity.SliderEntity;
import az.nijat_aliyev.zmm.repository.ImageRepository;
import az.nijat_aliyev.zmm.repository.SliderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SliderService {

    private final SliderRepository repository;
    private final ImageRepository imageRepository;

    public void upload(
            Long id,
            MultipartFile image
    ) {
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        SliderEntity sliderEntity = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST)
                );
        Long oldImageId = sliderEntity.getImageId();
        if (oldImageId != null) {
            imageRepository.delete(oldImageId);
        }
        String extension = image
                .getOriginalFilename()
                .split("\\.")[1];
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
