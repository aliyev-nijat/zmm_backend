package az.nijat_aliyev.zmm.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageEntity {

    private Long id;
    private String extension;
    private byte[] content;
}
