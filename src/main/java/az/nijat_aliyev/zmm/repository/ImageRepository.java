package az.nijat_aliyev.zmm.repository;

import az.nijat_aliyev.zmm.exception.ImageException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Repository
public class ImageRepository {
    private final String dbFolder;

    public ImageRepository() {
        dbFolder = Dotenv.load().get("DB_DIR_PATH") + "/images";
    }

    public void save(String name, byte[] file) {
        validateNameAndExtension(name);
        if (name.charAt(0) == '/') {
            name = name.substring(1);
        }
        Path path = Paths.get(dbFolder + "/" + name);
        try {
            Files.write(path, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateNameAndExtension(String name) {
        String[] splited;
        if (
                name == null ||
                        (splited = name.split("\\.")).length < 2 ||
                        Arrays.stream(splited)
                                .anyMatch(String::isBlank)
        ) {
            throw new ImageException("Image name can't be: " + name);
        }
        String extension = splited[splited.length - 1];
        if (
                !List.of(
                        "jpeg",
                        "jpg",
                        "png",
                        "gif",
                        "bmp",
                        "webp",
                        "tiff",
                        "tif",
                        "svg"
                ).contains(extension)
        ) {
            throw new ImageException("Image extension can't be: " + extension);
        }
    }
}
