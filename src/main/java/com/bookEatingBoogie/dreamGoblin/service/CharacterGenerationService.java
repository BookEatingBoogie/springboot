package com.bookEatingBoogie.dreamGoblin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;


@Service
public class CharacterGenerationService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${fastapi.baseUrl}")
    private String baseUrl;

    public String generateCharacter(String userImg) {

        System.out.println("▶ send to FastAPI imgUrl = " + userImg);

        //endpoint 경로 설정
        String fastAPIUrl = baseUrl + "/generate/character/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        /*
        fastAPI-generateCharacter 호출.
        생성된 캐릭터 이미지 경로(s3) 반환.
         */
        Map<String, String> body = Collections.singletonMap("imgUrl", userImg);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    fastAPIUrl, request, String.class
            );

            // fastAPI 호출이 됐지만, 응답이 비정상적으로 반환된 경우 예외 발생.
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("FastAPI 응답 오류: 상태코드 = " + response.getStatusCode());
            }
            // 정상적으로 호출 성공.
            return response.getBody();
        } catch (RestClientException e) {
            //예외 발생 시 예외 감싸서 던짐. 요청 자체가 실패한 경우.
            throw new RuntimeException("FastAPI 캐릭터 생성 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
