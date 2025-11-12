package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.model.entity.ImageEntity;
import az.aliyev_nijat.zmm.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository repository;

    public ImageEntity getImageById(Long id) {
        ImageEntity image = repository.getById(id);
        if (image == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Image does not exist"
            );
        }
        repository.loadContent(image);

        return image;
    }
}
