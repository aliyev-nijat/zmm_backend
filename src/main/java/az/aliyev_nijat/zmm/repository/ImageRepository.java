package az.aliyev_nijat.zmm.repository;

import az.aliyev_nijat.zmm.model.entity.AppConfiguration;
import az.aliyev_nijat.zmm.model.entity.ImageEntity;
import az.aliyev_nijat.zmm.model.entity.ImageExtension;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Repository
public class ImageRepository {

    private static final Logger log = LoggerFactory.getLogger(ImageRepository.class);

    private final String dbFolder;
    private final AppConfigurationRepository configurationRepository;

    public ImageRepository(AppConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
        dbFolder = Dotenv.load().get("DB_DIR_PATH") + "/images";
    }

    public ImageEntity create(ImageEntity image) {
        if (image.getId() != null || image.getExtension() == null) {
            throw new RuntimeException("ID must be null;extension can not be null");
        }
        Long id = generateId();
        image.setId(id);
        Path path = Paths.get(String.format(
                "%s/%d.%s",
                dbFolder,
                image.getId(),
                image.getExtension()
                        .toString()
                        .toLowerCase()
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
                        ImageExtension extension = ImageExtension.valueOf(
                                nameAndExtension[1].toUpperCase()
                        );
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
                        .toString()
                        .toLowerCase()
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
                        ImageExtension extension = ImageExtension.valueOf(
                                obj.nameAndExtension[1]
                                        .toUpperCase()
                        );
                    })
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
                        .toString()
                        .toLowerCase()
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
}
