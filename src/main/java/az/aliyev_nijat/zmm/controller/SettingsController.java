package az.aliyev_nijat.zmm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @GetMapping
    public Map<String, Object> getSettings() {
        Map<String, Object> result = new HashMap<>();
        result.put("contactPhone", "012 456 92 23");
        result.put("whatsapp","070 801 21 64");
        result.put("whatsappUrl", "https://wa.me/+994708012164");
        result.put("whatsappChannelName", "ZQMM “ Elanlar\uD83D\uDCE2 “");
        result.put("whatsappChannelUrl", "https://whatsapp.com/channel/0029VbAuvua9Bb61Btr2Cc3Q");
        result.put("tiktokUrl", "https://www.tiktok.com/@ziremedeniyyetmerkezi?_r=1&_t=ZS-91YSGEjJov6");
        result.put("tiktokUsername", "ziremedeniyyetmerkezi");
        result.put("workDays", "Hər gün");
        result.put("workHours", "9:00 - 18:00");
        result.put("instagramUrl", "https://www.instagram.com/ziremedeniyyetmerkezi/");
        result.put("instagramUsername", "ziremedeniyyetmerkezi");
        result.put("youtubeUrl", "https://www.youtube.com/@ZQMMK");
        result.put("youtubeUsername", "ZQMMK");
        result.put("about", """
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Eius facere iusto, sequi dolorem voluptatem magnam
                        cupiditate ea perspiciatis quas temporibus quisquam eos placeat culpa eveniet neque veritatis! Veniam, sed nemo
                        ea recusandae nostrum, voluptatum aperiam tempora ipsam ex totam distinctio? Corporis, ducimus consequuntur amet
                        laborum aspernatur cum nesciunt ipsa. Sint adipisci itaque repellendus doloribus fugit at facilis dolore vel
                        unde a veniam odit, iusto voluptas ipsum nihil corporis. Omnis sequi assumenda eum consectetur qui, sapiente in
                        dolorum laboriosam voluptatibus aspernatur. Voluptatem placeat voluptatibus ea? Debitis vitae magni, dolor,
                        accusamus officia ipsum nostrum sed ipsa at quidem hic! Natus et sint velit facere molestias. Rerum, aut! Vitae
                        aut quod consequatur perferendis quasi magnam dignissimos, excepturi quisquam necessitatibus a et, unde commodi
                        minima laboriosam velit expedita. Nam, voluptatem unde. Alias incidunt, perspiciatis itaque officiis aspernatur.""");
        result.put("imageUrl", "/assets/images/about/about.svg");
        return result;

    }

}
