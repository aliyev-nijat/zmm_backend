package az.nijat_aliyev.zmm.repository;

import az.nijat_aliyev.zmm.model.entity.AppConfiguration;
import az.nijat_aliyev.zmm.model.entity.ImageEntity;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class ImageRepository {

    private static final Logger log = LoggerFactory.getLogger(ImageRepository.class);
    private static final List<String> ALLOWED_EXTENSIONS = List.of(
            "jpeg",
            "jpg",
            "png",
            "gif",
            "bmp",
            "webp",
            "tiff",
            "tif",
            "svg"
    );
    private final String dbFolder;
    private final AppConfigurationRepository configurationRepository;

    public ImageRepository(AppConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
        dbFolder = Dotenv.load().get("DB_DIR_PATH") + "/images";
    }

    public ImageEntity create(ImageEntity image) {
        if (image.getId() != null) {
            throw new RuntimeException("ID must be null");
        }
        validateExtension(image.getExtension());
        Long id = generateId();
        image.setId(id);
        Path path = Paths.get(String.format(
                "%s/%d.%s",
                dbFolder,
                image.getId(),
                image.getExtension()
        ));
        try {
            Files.write(path, image.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return getById(id);
    }

    public ImageEntity getById(Long id) {
        if (!exists(id)) return null;
        Path folder = Paths.get(dbFolder);
        try {
            return Files.list(folder)
                    .filter(Files::isRegularFile)
                    .map(path -> path
                            .getFileName()
                            .toString()
                            .split("\\.")
                    )
                    .map(nameAndExtension -> new Object() {
                        Long id = Long.parseLong(nameAndExtension[0]);
                        String extension = nameAndExtension[1];
                    })
                    .filter(obj -> obj.id.equals(id))
                    .map(obj -> {
                        ImageEntity result = new ImageEntity();
                        result.setId(obj.id);
                        result.setExtension(obj.extension);

                        return result;
                    })
                    .findAny()
                    .orElse(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long id) {
        ImageEntity image = getById(id);
        if (image == null) return false;
        Path path = Paths.get(String.format(
                "%s/%d.%s",
                dbFolder,
                image.getId(),
                image.getExtension()
        ));
        try {

            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists(Long id) {
        Path folder = Paths.get(dbFolder);
        try {
            return Files.list(folder)
                    .filter(Files::isRegularFile)
                    .map(path -> new Object() {
                        Path filePath = path;
                        String[] nameAndExtension = path
                                .getFileName()
                                .toString()
                                .split("\\.");
                    })
                    .peek(obj -> {
                        if (obj.nameAndExtension.length != 2) {
                            log.error("Invalid image name({}) in db.", obj.filePath.getFileName());
                            throw new RuntimeException(
                                    "Image name can't be: " + obj.filePath.getFileName()
                            );
                        }
                    })
                    .map(obj -> new Object() {
                        Path filePath = obj.filePath;
                        Long id = Long.parseLong(obj.nameAndExtension[0]);
                        String extension = obj.nameAndExtension[1];
                    })
                    .peek(obj -> validateExtension(obj.extension))
                    .anyMatch(obj -> obj.id.equals(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadContent(ImageEntity image) {
        Path path = Paths.get(String.format(
                "%s/%d.%s",
                dbFolder,
                image.getId(),
                image.getExtension()
        ));
        if (Files.notExists(path)) {
            log.error("Invalid image object. {}.{}", image.getId(), image.getExtension());
            throw new RuntimeException("Invalid image object");
        }
        try {
            image.setContent(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Long generateId() {
        AppConfiguration config = configurationRepository.readData();
        Long id = config.getNextImageId();
        config.setNextImageId(id + 1);
        configurationRepository.writeData(config);

        return id;
    }

    private void validateExtension(String extension) {
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new RuntimeException("Extension can't be " + extension);
        }
    }

}
