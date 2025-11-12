package az.aliyev_nijat.zmm.repository;

import az.aliyev_nijat.zmm.model.entity.AppConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class AppConfigurationRepository {
    private static final Object FILE_LOCK = new Object();
    private final String dbFilePath;

    public AppConfigurationRepository() {
        dbFilePath = Dotenv.load().get("DB_DIR_PATH") + "/json/config.json";
    }

    public AppConfiguration readData() {
        ObjectMapper mapper = new ObjectMapper();
        synchronized (FILE_LOCK) {
            try {

                return mapper.readValue(new File(dbFilePath), AppConfiguration.class);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't read .json file.");
            }
        }
    }

    public void writeData(AppConfiguration data) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        synchronized (FILE_LOCK) {
            try {
                writer.writeValue(new File(dbFilePath), data);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't write .json file.");
            }
        }
    }
}
