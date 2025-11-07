package az.nijat_aliyev.zmm.repository.impl;

import az.nijat_aliyev.zmm.exception.DbException;
import az.nijat_aliyev.zmm.model.Event;
import az.nijat_aliyev.zmm.model.db.EventDb;
import az.nijat_aliyev.zmm.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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
    public synchronized Event create(@NonNull Event event) throws DbException {
        Long id = generateId();
        event.setId(id);
        EventDb data = readData();
        List<Event> list = new LinkedList<>(data.getEvents());
        list.add(event);
        data.setEvents(list);
        writeData(data);

        return getById(id);
    }

    @Override
    public Event getById(@NonNull Long id) {
        return readData()
                .getEvents()
                .stream()
                .filter(event -> event.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Event> findAll() {
        return readData().getEvents();
    }

    @Override
    public synchronized Event update(Event event) {
        return null;
    }

    @Override
    public synchronized void delete(Long id) {

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
                throw new RuntimeException(e);
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
                throw new RuntimeException(e);
            }
        }
    }
}
