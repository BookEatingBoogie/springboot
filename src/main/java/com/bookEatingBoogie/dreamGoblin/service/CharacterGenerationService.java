package com.bookEatingBoogie.dreamGoblin.service;

import com.bookEatingBoogie.dreamGoblin.DTO.GptPromptDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class CharacterGenerationService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${fastapi.baseUrl}")
    private String baseUrl;

    public String generateCharacter(GptPromptDTO prompt) {

        //endpoint 경로 설정
        String fastAPIUrl = baseUrl + "/generate/character/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        /*
        fastAPI-callGPT_character 호출.
        캐릭터 생성을 위한 질문 반환 - 사용자 입력 전달.
         */
        HttpEntity<GptPromptDTO> request = new HttpEntity<>(prompt, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    fastAPIUrl, request, String.class
            );

            // fastAPI 호출이 됐지만, 응답이 비정상적으로 반환된 경우 예외 발생.
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("FastAPI 응답 오류: 상태코드 = " + response.getStatusCode());
            }

            return response.getBody();
        } catch (RestClientException e) {
            //예외 발생 시 예외 감싸서 던짐. 요청 자체가 실패한 경우.
            throw new RuntimeException("FastAPI 캐릭터 생성 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
