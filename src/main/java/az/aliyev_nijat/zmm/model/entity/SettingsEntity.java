package az.aliyev_nijat.zmm.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "settings")
@Getter
@Setter
public class SettingsEntity {

    @Id
    private Long id;
    private String contactPhone;
    private String whatsapp;
    private String whatsappUrl;
    private String whatsappChannelName;
    private String whatsappChannelUrl;
    private String tiktokUrl;
    private String tiktokUsername;
    private String workDays;
    private String workHours;
    private String instagramUrl;
    private String instagramUsername;
    private String youtubeUrl;
    private String youtubeUsername;

    @Lob
    private String about;
    private String imageUrl;

}
