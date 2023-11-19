package org.dk.testtask.intrum.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class HttpDataSender {

    @Value("${data.upload.api}")
    private String dataUploadApi;

    public void send(PayoutDTO payout){
        CompletableFuture.runAsync(() -> {
            try {
                ObjectMapper jsonMapper = new ObjectMapper();
                HttpClient httpClient = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(dataUploadApi))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonMapper.writeValueAsString(payout)))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                HttpStatus status = HttpStatus.valueOf(response.statusCode());
                if(!HttpStatus.OK.equals(status)) {
                    throw new RuntimeException("Received status code " + status);
                }
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(ex->{
            log.error("Exception occurred while sending payout for company " + payout.getCompanyIdentityNumber() + " to collecting api.",ex);
            return null;
        });
    }
}
