package ru.skypro.homework.dto;


import lombok.*;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;

@Log4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class WebResultPhoto {

    private boolean result;
    private String message;
    private long size;

    private byte[] byteData;

    @Setter
    private HttpHeaders httpHeaders;

    public static WebResultPhoto resultErr (String mes) {

        return WebResultPhoto.builder()
                .result(false)
                .message(mes)
                .build();
    }

    public static WebResultPhoto getWebResultPhoto(byte[] data) {
        return WebResultPhoto.builder()
                .result(true)
                .message("ok")
                .byteData(data)
                .size(data.length)
                .build();
    }

    public WebResultPhoto(byte[] bytes, HttpHeaders httpHeaders) {
        result = true;
        message = "ok";

        this.byteData = bytes;
        this.httpHeaders = httpHeaders;
    }

}
