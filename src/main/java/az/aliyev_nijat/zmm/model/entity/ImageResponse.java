package az.aliyev_nijat.zmm.model.entity;

import org.springframework.http.MediaType;

public record ImageResponse(MediaType mimeType, byte[] content) {
}
