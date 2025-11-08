package az.nijat_aliyev.zmm.model.dto;

import az.nijat_aliyev.zmm.util.LocalDateTimeDeserializer;
import az.nijat_aliyev.zmm.util.LocalDateTimeSerializer;
import az.nijat_aliyev.zmm.validation.OnCreate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto {

    @Null(groups = OnCreate.class)
    private Long id;

    @NotNull(groups = OnCreate.class)
    private String title;

    @NotNull(groups = OnCreate.class)
    private String about;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;
}
