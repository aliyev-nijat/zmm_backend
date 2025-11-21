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
        result.put("whatsapp", "070 801 21 64");
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
                Zirə Qəsəbə Mədəniyyət Mərkəzi 8 dekabr 2008-ci il tarixində fəaliyyətə başlamışdır.
                Mərkəz yaradıldığı gündən etibarən qəsəbənin mədəni həyatını canlandırmaq,
                sakinlərin asudə vaxtını səmərəli təşkil etmək və müxtəlif sahələr üzrə bilik
                və bacarıqların inkişafına şərait yaratmaq məqsədi ilə fəaliyyət göstərir.
                2008–2022-ci illərdə Mədəniyyət Mərkəzinə Xumarə Əliyeva  rəhbərlik etmiş, bu dövrdə mərkəzin inkişafı, tədris kurslarının genişləndirilməsi və icma ilə mədəni əlaqələrin gücləndirilməsi istiqamətində mühüm addımlar atılmışdır.
                2023-cü ildən etibarən Mərkəzə Röyal Cəbiyev  təyin olunmuş, rəhbərlikdəki yenilənmə ilə mədəni, sosial və yaradıcı fəaliyyətlər daha da genişləndirilmişdir. Bu mərhələdə Mədəniyyət Mərkəzi həm ənənəvi kursları, həm də müasir dövrün tələblərinə uyğun yeni tədris istiqamətlərini işə salaraq fəaliyyətini daha da diversifikasiya etmişdir.
                Mərkəz fəaliyyət göstərdiyi dövrdə müxtəlif sahələr üzrə kurslar təşkil edib, yüzlərlə uşağın və gəncin inkişafına töhfə vermişdir. Bu kurslara aşağıdakılar daxildir:
                Rəqs
                Komputer\s
                İngilis dili
                Şahmat
                Proqramlaşdırma / proqramist hazırlığı
                Gitara
                Fortepiano
                Vizaj\s
                Dərzilik
                Empathy Teatr\s
                Bu kurslar vasitəsilə uşaqlar, yeniyetmələr və gənclər həm yaradıcı, həm də 
                praktiki sahələrdə öz bacarıqlarını inkişaf etdirmək imkanı əldə etmişlər.
                Bu gün Zirə Qəsəbə Mədəniyyət Mərkəzi öz fəaliyyətini davam etdirərək qəsəbənin 
                sosial-mədəni həyatının əsas mərkəzlərindən biri kimi çıxış edir. Mərkəz həm ənənəvi incəsənət 
                sahələrini qoruyub inkişaf etdirir, həm də müasir bilik və bacarıqların tədrisinə geniş yer verir.""");
        result.put("imageUrl", "/assets/images/about/about.svg");
        return result;

    }

}
