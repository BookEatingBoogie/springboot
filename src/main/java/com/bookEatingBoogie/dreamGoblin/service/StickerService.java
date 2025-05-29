package com.bookEatingBoogie.dreamGoblin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class StickerService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${fastapi.baseUrl}")
    private String fastapiBaseUrl;

    public List<String> getStickerList() {
        String url = fastapiBaseUrl + "/stickers";

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    List.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("FastAPI 응답 오류");
            }

        } catch (Exception e) {
            throw new RuntimeException("FastAPI 스티커 목록 요청 실패: " + e.getMessage(), e);
        }
    }

}
