package az.nijat_aliyev.zmm.repository.impl;

import az.nijat_aliyev.zmm.model.entity.EventEntity;
import az.nijat_aliyev.zmm.model.db.EventDb;
import az.nijat_aliyev.zmm.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class JsonEventRepository implements EventRepository {

    private static final Object FILE_LOCK = new Object();
    private final String dbFilePath;

    public JsonEventRepository() {
        dbFilePath = Dotenv.load().get("DB_DIR_PATH") + "/json/events.json";
    }

    @Override
    public synchronized EventEntity create(@NonNull EventEntity event) {
        Long id = generateId();
        event.setId(id);
        EventDb data = readData();
        List<EventEntity> list = new LinkedList<>(data.getEvents());
        list.add(event);
        data.setEvents(list);
        writeData(data);

        return getById(id);
    }

    @Override
    public EventEntity getById(@NonNull Long id) {
        return readData()
                .getEvents()
                .stream()
                .filter(event -> event.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<EventEntity> findAll() {
        return readData().getEvents();
    }

    @Override
    public synchronized EventEntity update(@NonNull EventEntity event) {
        EventDb db = readData();
        Long id = event.getId();
        db.setEvents(
                db.getEvents()
                        .stream()
                        .map(e ->
                                id.equals(e.getId()) ?
                                        event :
                                        e
                        )
                        .toList()
        );

        return getById(id);
    }

    @Override
    public synchronized void delete(Long id) {
        EventDb db = readData();
        db.setEvents(
                db.getEvents()
                        .stream()
                        .filter(event -> !event.getId().equals(id))
                        .toList()
        );
        writeData(db);
    }

    private synchronized Long generateId() {
        EventDb data = readData();
        Long nextId = data.getNextId();
        data.setNextId(nextId + 1);
        writeData(data);

        return nextId;
    }

    private EventDb readData() {
        ObjectMapper mapper = new ObjectMapper();
        synchronized (FILE_LOCK) {
            try {

                return mapper.readValue(new File(dbFilePath), EventDb.class);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't read .json file.");
            }
        }
    }

    private void writeData(EventDb data) {
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
