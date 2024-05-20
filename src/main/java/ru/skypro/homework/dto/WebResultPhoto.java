package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;

@Log4j
@Getter
@Builder
@AllArgsConstructor
public final class WebResultPhoto {

    private boolean result;
    private String message;
    private long size;

    private byte[] byteData;
    private HttpHeaders httpHeaders;

    public static WebResultPhoto resultErr (String mes) {

        return WebResultPhoto.builder()
                .result(false)
                .message(mes)
                .build();
    }

    public WebResultPhoto(byte[] bytes, HttpHeaders httpHeaders) {
        result = true;
        message = "ok";

        this.byteData = bytes;
        this.httpHeaders = httpHeaders;
    }

}
