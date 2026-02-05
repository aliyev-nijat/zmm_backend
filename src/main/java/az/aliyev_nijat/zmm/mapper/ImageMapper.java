package az.aliyev_nijat.zmm.mapper;

import az.aliyev_nijat.zmm.model.entity.ImageResponse;
import az.aliyev_nijat.zmm.model.entity.ImageEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageResponse map(ImageEntity source, byte[] content) {
        ImageResponse target = new ImageResponse(
                MediaType.parseMediaType(source.getMimeType()),
                content
        );

        return target;
    }
}
