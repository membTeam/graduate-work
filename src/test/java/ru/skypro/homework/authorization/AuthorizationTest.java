package ru.skypro.homework.authorization;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import ru.skypro.homework.repositories.AdvertisementRepository;


/**
 * Тестирование авторизации
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationTest {

    @Autowired
    private AdvertisementRepository advertisementRepo;

    @LocalServerPort
    private int port;

    @Test
    public void allAd() throws URISyntaxException, IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://localhost:"+port+"/ads"))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    public void adById_status401() throws URISyntaxException, IOException, InterruptedException {
        var id = advertisementRepo.getMinId();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://localhost:"+port+"/ads/"+ id))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(401, response.statusCode());
    }


    @Test
    public void adById_status200() throws URISyntaxException, IOException, InterruptedException {
        var id = advertisementRepo.getMinId();

        String username = "user123@mail.ru", password = "updpassword";
        byte[] chars = (username+':'+password).getBytes();

        var strEncoder  = String.format("Basic %s",
                Base64.getEncoder().encodeToString(chars) );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://localhost:"+port+"/ads/" + id))
                .header("Authorization", strEncoder)
                .build();

        HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

    }

}
