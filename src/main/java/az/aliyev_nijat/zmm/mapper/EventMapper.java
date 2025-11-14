package az.aliyev_nijat.zmm.mapper;

import az.aliyev_nijat.zmm.model.dto.EventDto;
import az.aliyev_nijat.zmm.model.entity.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventEntity map(final EventDto source) {
        EventEntity target = new EventEntity();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setAbout(source.getAbout());
        target.setDateTime(source.getDateTime());
        target.setImageUrl(source.getImageUrl());
        target.setImageId(source.getImageId());
        target.setOrder(source.getOrder());

        return target;
    }

    public EventDto map(final EventEntity source) {
        EventDto target = new EventDto();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setAbout(source.getAbout());
        target.setDateTime(source.getDateTime());
        target.setImageUrl(source.getImageUrl());
        target.setImageId(source.getImageId());
        target.setOrder(source.getOrder());

        return target;
    }
}
