package az.aliyev_nijat.zmm.service;

import az.aliyev_nijat.zmm.model.entity.EventEntity;
import az.aliyev_nijat.zmm.model.entity.SliderEntity;
import az.aliyev_nijat.zmm.model.entity.UserEntity;
import az.aliyev_nijat.zmm.repository.CourseRepository;
import az.aliyev_nijat.zmm.repository.EventRepository;
import az.aliyev_nijat.zmm.repository.SliderRepository;
import az.aliyev_nijat.zmm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestoreService {
    private final CourseRepository courseRepository;
    private final EventRepository eventRepository;
    private final SliderRepository sliderRepository;
    private final UserRepository userRepository;

    public Map<String, Object> getRestore() {
        Map<String, Object> result = new HashMap<>();
        result.put("courses", courseRepository.findAll());
        result.put("events", eventRepository.findAll());
        result.put("sliders", sliderRepository.findAll());
        result.put("users", userRepository.findAll());

        return result;
    }

    public void restore() {
        List<SliderEntity> sliders = new LinkedList<>();
        sliders.add(new SliderEntity());
        sliders.getLast().setImageUrl("/api/images/30");
        sliders.getLast().setImageId(30L);
        sliders.add(new SliderEntity());
        sliders.getLast().setImageUrl("/api/images/29");
        sliders.getLast().setImageId(29L);
        sliders.add(new SliderEntity());
        sliders.getLast().setImageUrl("/api/images/34");
        sliders.getLast().setImageId(34L);
        sliders.add(new SliderEntity());
        sliders.getLast().setImageUrl("/api/images/31");
        sliders.getLast().setImageId(31L);
        sliders.add(new SliderEntity());
        sliders.getLast().setImageUrl("/api/images/35");
        sliders.getLast().setImageId(35L);
        sliders.add(new SliderEntity());
        sliders.getLast().setImageUrl("/api/images/33");
        sliders.getLast().setImageId(33L);
        List<EventEntity> events = new LinkedList<>();
        events.add(new EventEntity());
        events.getLast().setImageId(37L);
        events.getLast().setImageUrl("/api/images/37");
        events.getLast().setOrder(7L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-10-11T19:00"));
        events.getLast().setTitle("""
                Maarif masası""");
        events.getLast().setAbout("""
                Bizim yeni podcast buraxılışımızda Zirə qəsəbə Mədəniyyət  Mərkəzinin müəllimləri öz hekayələrini danışır. Onlar bu sahəyə necə gəliblər, hansı çətinlikləri və uğurları yaşayıblar, bu işin onların həyatına necə təsir etdiyini səmimi şəkildə bölüşürlər. Dinləyin, ilham alın və bizim komandanı daha yaxından tanıyın!Podcast 1-ci bölüm: Əliyeva Əhdiyyə- Zirə qəsəbə Mədəniyyət Mərkəzinin Rəqs kursunun rəhbəri<br><iframe width="951" height="535" src="https://www.youtube.com/embed/cMkMx37GM68" title="ZQMM - Maarif Masası Podcast — 1-ci bölüm." frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""");
        events.add(new EventEntity());
        events.getLast().setImageId(41L);
        events.getLast().setImageUrl("/api/images/41");
        events.getLast().setOrder(2L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-04-20T13:00"));
        events.getLast().setTitle("""
                Daun sindiromlu uşaqların əyləncəsi konsert proqramı""");
        events.getLast().setAbout("""
                20 Aprel- Zirə qəsəbə Mədəniyyət Mərkəzində Daun sindiromlu uşaqlar üçün xüsusi konsert proqramı təşkil olundu. Konsert proqramı Zirə qəsəbə Mədəniyyət Mərkəzi, "Nur İctimai Birliyi" və "El" Yaradıcılıq Mərkəzinin birgə təşkilatçılığı ilə həyata keçirilmişdir. Konsert proqramı daxilində "El Yaradıclıq Mərkəzinin" üzvləri ilə birgə Daun sindiromlu uşaqların çıxışları oldu. Sonda uşaqlara xüsusi hədiyyələr təqdim edildi və xatirə şəkilləri çəkildi.""");
        events.add(new EventEntity());
        events.getLast().setImageId(38L);
        events.getLast().setImageUrl("/api/images/38");
        events.getLast().setOrder(8L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-10-17T19:00"));
        events.getLast().setTitle("""
                Maarif masası""");
        events.getLast().setAbout("""
                Bizim yeni podcast buraxılışımızda Zirə qəsəbə Mədəniyyət  Mərkəzinin müəllimləri öz hekayələrini danışır. Onlar bu sahəyə necə gəliblər, hansı çətinlikləri və uğurları yaşayıblar, bu işin onların həyatına necə təsir etdiyini səmimi şəkildə bölüşürlər. Dinləyin, ilham alın və bizim komandanı daha yaxından tanıyın!Podcast 2-ci bölüm: Zirə qəsəbə Mədəniyyət Mərkəzinin Vizaj kursunun rəhbəri-Mirzəyeva Aysel<iframe width="695" height="391" src="https://www.youtube.com/embed/uRj_S7oEnb8" title="ZQMM - Maarif Masası Podcast - 2-ci bölüm" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""");
        events.add(new EventEntity());
        events.getLast().setImageId(42L);
        events.getLast().setImageUrl("/api/images/42");
        events.getLast().setOrder(1L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-03-16T16:00"));
        events.getLast().setTitle("""
                "Zirədə bahar şəfəqi" -Novruz şənliyi""");
        events.getLast().setAbout("""
                "Zirədə Bahar şəfəqi"adlı Novruz şənliyi keçirilmişdir.Bu tədbirin keçirilməsində əsas məqsəd milli bayramımız olan Novruzu qeyd etmək və onun adət-ənənələrini gələcək nəsillərə ötürməkdir. 3 gün davam edən şənliyin ilk günündə müxtəlif musiqi alətləri(saz,fleyta), "Zirə Mədəniyyət ansamblı" həmçinin "İnci" rəqs ansamblının çıxışları baş tutmuşdur.O cümlədən  keçəl və kosanın maraqlı dialoqları da günü daha maraqlı etdi. Zirə qəsəbə Mədəniyyət Mərkəzinin "Ay" rəqs ansamblının çıxışları ilə günü daha da əyləncəli hala çevirdi. Həmçinin qəsəbə sakininlərinin dəstəyi ilə əl işləri ilə məşğul olan kiçik sahibkarlar da öz əl işlərini şənliyin keçirildiyi müddətdə nümayiş etdirdilər.""");
        events.add(new EventEntity());
        events.getLast().setImageId(39L);
        events.getLast().setImageUrl("/api/images/39");
        events.getLast().setOrder(9L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-10-24T19:00"));
        events.getLast().setTitle("""
                Maarif masası""");
        events.getLast().setAbout("""
                Bizim yeni podcast buraxılışımızda Zirə qəsəbə Mədəniyyət  Mərkəzinin müəllimləri öz hekayələrini danışır. Onlar bu sahəyə necə gəliblər, hansı çətinlikləri və uğurları yaşayıblar, bu işin onların həyatına necə təsir etdiyini səmimi şəkildə bölüşürlər. Dinləyin, ilham alın və bizim komandanı daha yaxından tanıyın!Podcast 3-cü bölüm: Zirə qəsəbə Mədəniyyət Mərkəzinin rəsm kursunun rəhbəri- Hacıyeva Əminə<iframe width="695" height="391" src="https://www.youtube.com/embed/_h5YCm3m_-o" title="ZQMM - Maarif Masası Podcast - 3-cü bölüm" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""");
        events.add(new EventEntity());
        events.getLast().setImageId(40L);
        events.getLast().setImageUrl("/api/images/40");
        events.getLast().setOrder(10L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-11-07T19:00"));
        events.getLast().setTitle("""
                Maarif masası""");
        events.getLast().setAbout("""
                Bizim yeni podcast buraxılışımızda Zirə qəsəbə Mədəniyyət  Mərkəzinin müəllimləri öz hekayələrini danışır. Onlar bu sahəyə necə gəliblər, hansı çətinlikləri və uğurları yaşayıblar, bu işin onların həyatına necə təsir etdiyini səmimi şəkildə bölüşürlər. Dinləyin, ilham alın və bizim komandanı daha yaxından tanıyın!Podcast 4-cü bölüm: Zirə qəsəbə Mədəniyyət Mərkəzinin rəsm kursunun rəhbəri-Əliyeva Əsmər<iframe width="695" height="391" src="https://www.youtube.com/embed/5gqE_QhP6hA" title="ZQMM - Maarif Masası Podcast - 5-ci bölüm. (Anons)" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""");
        events.add(new EventEntity());
        events.getLast().setImageId(8L);
        events.getLast().setImageUrl("/api/images/8");
        events.getLast().setOrder(3L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-08-12T12:00"));
        events.getLast().setTitle("""
                Şəhid, qazi və aztəminatlı ailələrlə ilə görüş""");
        events.getLast().setAbout("""
                12-Avqust- Zirə qəsəbə Mədəniyyət Mərkəzinin "Empathy" teatrı, Zirə Cümə Məscidi və Quba rayon İcra Hakimiyyətinin əməkdaşlığı ilə birgə Quba şəhərində aztəminatlı,şəhid və qazi ailələrinə ehsan süfrəsi verildi. Təşkil edilmiş ehsan süfrəsində "Empathy" teatrın üzvləri "Yağışı sevməyən uşaq" adlı tamaşanı nümayiş etdirdilər. """);
        events.add(new EventEntity());
        events.getLast().setImageId(9L);
        events.getLast().setImageUrl("/api/images/9");
        events.getLast().setOrder(5L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-08-25T16:00"));
        events.getLast().setTitle("""
                " Təqdir" qısametrajlı filmin nümayişi""");
        events.getLast().setAbout("""
                25 Avqsut- Zirə qəsəbə Mədəniyyət Mərkəzinin istehsalı olan "Təqdir" qısametrajlı filminin qala gecəsi baş tutdu. Film narkomaniyaya qarşı və onunla mübarizə mövzusuna həsr olunmuşdu. Qala gecəsində bir sıra dəyərli  qonaqlar və filmdə iştirak etmiş aktyorlar çıxışları ilə film barəsində fikirlərini bildirdilər. Qala gecəsinin ərsəyə gəlməsində dəstəyi olan Nizami rayon İcra Hakimiyyəti, Sevinc Mall rəhbərliyinə təşəkkür bildirilərək, sonda xatirə şəkilləri çəkildi. <iframe width="695" height="391" src="https://www.youtube.com/embed/Z6JVp951MhM" title="&quot;Təqdir&quot;" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>""");
        events.add(new EventEntity());
        events.getLast().setImageId(10L);
        events.getLast().setImageUrl("/api/images/10");
        events.getLast().setOrder(11L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-11-16T17:00"));
        events.getLast().setTitle("""
                "Fiqaronun toyu" tamaşası""");
        events.getLast().setAbout("""
                Əsərin mərkəzində ağıllı, hazırcavab və azad ruhlu Figaro durur. O, öz ağası Qraf Almavivanın oyunlarına qarşı çıxaraq, sevdiyi Suzanna ilə evlənmək istəyir. Qraf isə Suzannanı ələ keçirmək üçün müxtəlif hiylələr qurur. Amma Figaro öz zəkası və çevikliyi ilə bu planları pozur, sonda ədalət və sevgi qalib gəlir.Tarix: 16 Noyabr Saat: 17:00""");
        events.add(new EventEntity());
        events.getLast().setImageId(11L);
        events.getLast().setImageUrl("/api/images/11");
        events.getLast().setOrder(6L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-08-28T11:00"));
        events.getLast().setTitle("""
                Bəxtiyar Vahabzadənin 100 illiyinə həsr olunmuş qiraət yarışması""");
        events.getLast().setAbout("""
                28 avqust 2025-ci il tarixində Zirə Mədəniyyət Mərkəzində böyük Azərbaycan şairi Bəxtiyar Vahabzadənin 100 illiyinə həsr olunmuş qiraət müsabiqəsi keçirilmişdir.Tədbirin məqsədi klassik ədəbi irsimizi gənc nəsil arasında təbliğ etmək, bədii qiraət mədəniyyətini inkişaf etdirmək və Bəxtiyar Vahabzadənin yaradıcılığını yeni nəsillərə tanıtmaq olmuşdur. Müsabiqənin xüsusi qonağı və münsiflər heyətinin üzvü tanınmış şair və yazıçı İlqar Fəhmi olmuşdur. Qiraətçilər şairin müxtəlif şeirlərindən parçalar səsləndirərək, duyğulu çıxışları ilə tamaşaçılar və münsiflər tərəfindən yüksək qiymətləndirilmişlər. Yarışmanın sonunda qaliblərə diplomlar, hədiyyələr, sertifikatlar və təşəkkürnamələr təqdim edilmişdir. Tədbir iştirakçılarının çıxışları və ümumi atmosferi mədəniyyətimizin zənginliyini, poeziyamızın dərinliyini bir daha nümayiş etdirmişdir.""");
        events.add(new EventEntity());
        events.getLast().setImageId(12L);
        events.getLast().setImageUrl("/api/images/12");
        events.getLast().setOrder(4L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-10-04T11:00"));
        events.getLast().setTitle("""
                Şəhid, qazi və aztəminatlı ailələrlə görüş""");
        events.getLast().setAbout("""
                Oktyabrın 4-də Zirə Bakı Şəhər Mədəniyyət Baş İdarəsinin tabeliyində olan Zirə qəsəbə Mədəniyyət Mərkəzi, Şəki Rayon İcra Hakimiyyəti, Şəki- Zaqatala Regional Mədəniyyət İdarəsinin birgə təşkilatçılığı ilə Şəki şəhərində tədbir keçirilib. Tədbirin keçirilməsində əsas məqsəd şəhid,qazi və aztəminatlı ailələrin övladlarını mədəniyyət və incəsənət ilə tanış etmək, onlara xoş anlar yaşatmaq olub. Tədbir çərçivəsində mərkəz tərəfindən " Yağışı sevməyən uşaq" tamaşaası nümayiş etdirilib.""");
        events.add(new EventEntity());
        events.getLast().setImageId(36L);
        events.getLast().setImageUrl("/api/images/36");
        events.getLast().setOrder(12L);
        events.getLast().setDateTime(LocalDateTime.parse("2025-11-21T12:00"));
        events.getLast().setTitle("""
                Bəhram Bağırzadə ilə görüş” adlı yaradıcı tədbir """);
        events.getLast().setAbout("""
                Zirə qəsəbə Mədəniyyət Mərkəzində “Bəhram Bağırzadə ilə görüş” adlı yaradıcı tədbir keçiriləcək.Tədbir Zirə qəsəbə ibtidai sinif şagirdləri üçün nəzərdə tutulub. Görüşün əvvəlində uşaqlar sevimli qonaqları üçün rəngarəng rəqs nömrəsi təqdim edəcəklər.Daha sonra uşaqlar tanınmış sənətçi, aktyor və yazıçı Bəhram Bağırzadə ilə görüşəcək, ona suallar verəcək və öz istedadlarını nümayiş etdirəcəklər.Səhnədə Bəhram Bağırzadə üçün xüsusi mühit — kreslo, jurnal masası və onun kitablarının sərgiləndiyi şkaflarla bəzədilmiş dekor yaradılacaq. Sənətçi uşaqlarla səmimi söhbət edəcək, onlara yaradıcılıq və oxu sevgisi haqqında dəyərli tövsiyələr verəcək, fəal iştirakçılara kitablarını hədiyyə edəcək.Tədbirin sonunda uşaqlara Zirə qəsəbə Mədəniyyət Mərkəzinin “Empathy” dram studiyasının təqdimatında “Figaronun toyu” tamaşası nümayiş etdiriləcək.""");
        List<UserEntity> users = new LinkedList<>();
        users.add(new UserEntity());
        users.getLast().setUsername("root");
        users.getLast().setPasswordHash("NxY4gKRZCsLwe5xBtqeIcdA+ogS6o/ejIjPx5EEzo24=");
        users.getLast().setRole("ROLE_ROOT");
        users.add(new UserEntity());
        users.getLast().setUsername("nijat");
        users.getLast().setPasswordHash("rkSQxgc6eiJOmwFtmlc8kfgGUXcAmAc737q3aTqD3bU=");
        users.getLast().setRole("ROLE_ADMIN");
        users.add(new UserEntity());
        users.getLast().setUsername("nigar");
        users.getLast().setPasswordHash("g7yvTnNQFjT9zo172M7K0JeaDN8U/h6odkGh3bl+vps=");
        users.getLast().setRole("ROLE_ADMIN");
        sliders.forEach(sliderRepository::save);
        events.forEach(eventRepository::save);
        users.forEach(userRepository::save);
    }
}
