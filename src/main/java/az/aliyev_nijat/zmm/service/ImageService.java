package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.mapper.ImageMapper;
import az.aliyev_nijat.zmm.model.entity.ImageResponse;
import az.aliyev_nijat.zmm.model.entity.ImageEntity;
import az.aliyev_nijat.zmm.model.entity.ImageExtension;
import az.aliyev_nijat.zmm.repository.ImageRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
public class ImageService {

    private final ImageRepository repository;
    private final ImageMapper mapper;
    private final String dbFolder;

    public ImageService(ImageRepository repository, ImageMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        dbFolder = Dotenv.load().get("DB_DIR_PATH");
    }

    @Transactional
    public Long create(@NonNull MultipartFile image) {
        validateImage(image);
        String extension;
        String[] splited = image.getOriginalFilename().split("\\.");
        extension = splited[splited.length - 1];
        byte[] content;
        try {
            content = image.getBytes();
        } catch (IOException e) {
            log.error("Failed to read image content");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to read image content",
                    e
            );
        }
        ImageEntity entity = new ImageEntity();
        entity.setPath(dbFolder);
        entity.setMimeType("image/" + extension);
        entity = repository.save(entity);
        entity.setPath(String.format(
                "%s/%d.%s",
                dbFolder,
                entity.getId(),
                extension
        ));
        entity = repository.save(entity);
        writeContent(entity.getPath(), content);

        return entity.getId();
    }

    public ImageResponse getImageById(Long id) {
        return repository
                .findById(id)
                .map(entity ->
                        mapper.map(entity, getContent(entity.getPath()))
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Image does not exist"
                        ));
    }

    @Transactional
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "Id must not be null");
        ImageEntity image = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image doesn't exist with given id")
        );
        Path path = Path.of(image.getPath());
        try {
            if (!Files.deleteIfExists(path)) {
                log.warn("File does not exist {}", image);
            }
        } catch (IOException e) {
            log.error("Failed to delete image file. Path: {}, Image: {}", path, image, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        repository.deleteById(id);
    }

    private void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image must be provided"
            );
        }
        if (
                image.getContentType() == null ||
                        !image.getContentType().startsWith("image/")
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid content type"
            );
        }
        if (image.getOriginalFilename() == null || !image.getOriginalFilename().contains(".")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid file name"
            );
        }
        String[] splited = image.getOriginalFilename().split("\\.");
        String extension = splited[splited.length - 1].toLowerCase();
        Arrays.stream(ImageExtension.values())
                .map(ImageExtension::name)
                .map(String::toLowerCase)
                .filter(value -> value.equals(extension))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid file extension"
                ));
    }

    private void writeContent(
            @NonNull String pathString,
            byte[] content
    ) {
        Path path = Paths.get(pathString);
        try {
            Files.write(path, content);
        } catch (IOException e) {
            log.error("Can't write image to disk. Url: {} ", pathString);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Image can't write."
            );
        }
    }

    private byte[] getContent(String pathString) {
        Path path = Paths.get(pathString);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Can't read image from disk. Url: {} ", pathString);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Image can't access."
            );
        }
    }
}
