package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.repository.CourseRepository;
import az.aliyev_nijat.zmm.repository.EventRepository;
import az.aliyev_nijat.zmm.repository.SliderRepository;
import az.aliyev_nijat.zmm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestoreService {
    private final CourseRepository courseRepository;
    private final EventRepository eventRepository;
    private final SliderRepository sliderRepository;
    private final UserRepository userRepository;

    public Map<String, Object> getRestore() {
        Map<String, Object> result = new HashMap<>();
        result.put("courses", courseRepository.findAll());
        result.put("events", eventRepository.findAll());
        result.put("sliders", sliderRepository.findAll());
        result.put("users", userRepository.findAll());

        return result;
    }
}
