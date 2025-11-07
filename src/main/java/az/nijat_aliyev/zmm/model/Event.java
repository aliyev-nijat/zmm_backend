package az.nijat_aliyev.zmm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Event {

    private Long id;
    private String title;
    private String about;
    private LocalDateTime dateTime;
}
