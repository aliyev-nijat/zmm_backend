package az.nijat_aliyev.zmm.model.db;

import az.nijat_aliyev.zmm.model.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventDb {

    private Long nextId;
    private List<Event> events;
    private List<Long> order;
}
