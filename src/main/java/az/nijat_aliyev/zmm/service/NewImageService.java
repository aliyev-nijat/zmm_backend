package az.nijat_aliyev.zmm.service;

import az.nijat_aliyev.zmm.model.entity.ImageEntity;
import az.nijat_aliyev.zmm.repository.NewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class NewImageService {

    private final NewImageRepository repository;

    public byte[] getImageById(Long id) {
        ImageEntity image = repository.getById(id);
        if (image == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Image does not exist"
            );
        }
        repository.loadContent(image);

        return image.getContent();
    }
}
