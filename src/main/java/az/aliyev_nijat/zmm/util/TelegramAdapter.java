package az.aliyev_nijat.zmm.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
public class TelegramAdapter {

    private final String botToken;
    private final String chatId;

    private String generateUrl(String message) {
        return String.format(
                "https://api.telegram.org/bot%s/sendmessage?chat_id=%s&text=%s",
                botToken,
                chatId,
                message
        );
    }

    public void sendMessage(String message) {
        RestTemplate rest = new RestTemplate();
        log.debug(rest.getForObject(generateUrl(message), String.class));
    }
}
