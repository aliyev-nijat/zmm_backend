package az.nijat_aliyev.zmm.controller;

import az.nijat_aliyev.zmm.model.Event;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/events")
public class EventController {

    private final List<Event> events;

    public EventController() {
        this.events = List.of(
                new Event(1L, "Tədbir 1", "Hörmətli musiqisevərlər!\n" +
                        "\n" +
                        "Sizi zərif səs və ilahi melodiyaların hökm sürdüyü möhtəşəm klassik musiqi konsertinə dəvət edirik. Bu xüsusi axşamda dünya bəstəkarlarının ölməz əsərləri canlı ifada səslənəcək.\n" +
                        "\n" +
                        "Səhnədə tanınmış musiqiçilər və gənc istedadların ifasında klassik musiqinin sehrinə qərq olun.\n" +
                        "\u2028Gözəl musiqi ilə dolu bu axşam sizə ruhən zövq, qəlbinizə rahatlıq bəxş edəcək."
                , "2025-12-5-19-00"),
                new Event(2L, "Tədbir 2", "Hörmətli musiqisevərlər!\n" +
                        "\n" +
                        "Sizi zərif səs və ilahi melodiyaların hökm sürdüyü möhtəşəm klassik musiqi konsertinə dəvət edirik. Bu xüsusi axşamda dünya bəstəkarlarının ölməz əsərləri canlı ifada səslənəcək.\n" +
                        "\n" +
                        "Səhnədə tanınmış musiqiçilər və gənc istedadların ifasında klassik musiqinin sehrinə qərq olun.\n" +
                        "\u2028Gözəl musiqi ilə dolu bu axşam sizə ruhən zövq, qəlbinizə rahatlıq bəxş edəcək."
                        , "2025-12-9-12-00")
        );
    }

    @GetMapping
    public List<Event> findAll() {
        return events;
    }
}
