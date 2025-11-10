package az.nijat_aliyev.zmm.service;

import az.nijat_aliyev.zmm.exception.EventNotFoundException;
import az.nijat_aliyev.zmm.exception.ImageException;
import az.nijat_aliyev.zmm.model.entity.EventEntity;
import az.nijat_aliyev.zmm.repository.EventRepository;
import az.nijat_aliyev.zmm.repository.ImageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final EventRepository eventRepository;
    private final ImageRepository repository;

    public String upload(
            @NonNull String dir,
            @NonNull String name,
            @NonNull MultipartFile image
    ) {
        validate(dir, name, image);
        if (
                dir.equals("events") &&
                        splitByDash(name).length == 1
        ) {
            return uploadEventImage(name, image);
        } else {
            return null;
        }
    }

    private String uploadEventImage(
            String name,
            MultipartFile image
    ) {
        Long eventId;
        try {
            eventId = Long.parseLong(name);
        } catch (NumberFormatException e) {
            throw new ImageException(
                    String.format(
                            "%s is not valid id",
                            name
                    )
            );
        }
        EventEntity event = eventRepository.getById(eventId);
        if (event == null) {
            throw new EventNotFoundException("Event not found with given id");
        }
        String[] splitedFileName = image.getOriginalFilename().split("\\.");
        String newFilename = String.format(
                "events/%d.%s",
                eventId,
                splitedFileName[splitedFileName.length - 1]
        );
        byte[] content;
        try {
            content = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String oldUrl = event.getImageUrl();
        if (oldUrl != null) {
            repository.delete(oldUrl.substring(11));
        }
        repository.save(newFilename, content);
        String url = "/api/images" + "/" + newFilename;
        event.setImageUrl(url);
        eventRepository.update(event);

        return url;
    }

    public void deleteEventImage(Long id) {
        EventEntity event = eventRepository.getById(id);
        String url = event.getImageUrl();
        if (url != null) {
            repository.delete(url.substring(11));
            event.setImageUrl(null);
            eventRepository.update(event);
        }
    }

    private void delete(String url) {
        repository.delete(url.substring(11));
    }

    private String[] splitByDash(String name) {
        return name.split("-");
    }

    private void validate(
            String dir,
            String name,
            MultipartFile image
    ) {
        if (
                dir == null ||
                        name == null ||
                        image == null ||
                        image.isEmpty()
        ) throw new ImageException("Validation exception");
    }

    public byte[] getImage(String path) {
        return repository.getImage(path);
    }
}
