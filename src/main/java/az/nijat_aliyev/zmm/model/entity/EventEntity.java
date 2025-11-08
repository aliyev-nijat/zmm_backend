package az.nijat_aliyev.zmm.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import az.nijat_aliyev.zmm.util.LocalDateTimeSerializer;
import az.nijat_aliyev.zmm.util.LocalDateTimeDeserializer;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EventEntity {

    private Long id;
    private String title;
    private String about;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;
}
