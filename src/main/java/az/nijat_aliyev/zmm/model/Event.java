package az.nijat_aliyev.zmm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Event {

    private Long id;
    private String title;
    private String about;
    private String dateTime;
}
