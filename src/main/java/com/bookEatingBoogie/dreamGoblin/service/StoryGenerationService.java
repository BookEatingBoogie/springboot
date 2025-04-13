package com.bookEatingBoogie.dreamGoblin.service;

import com.bookEatingBoogie.dreamGoblin.DTO.StoryInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StoryGenerationService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${fastapi.baseUrl}")
    private String baseUrl;

    public List<String> generateStory(StoryInfoDTO storyInfo) {

        //endpoint 경로 설정.
        String fastAPIUrl = baseUrl + "/generate/story/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        /*
        fastAPI-generateStory 호출.
        스토리 생성을 위한 스타일값 전달, 생성된 동화 내용 반환.
         */
        HttpEntity<StoryInfoDTO> request = new HttpEntity<>(storyInfo, headers);

        try {
            ResponseEntity<List<String>> response = restTemplate.exchange(
                    fastAPIUrl,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<String>>() {
                    }
            );

            // fastAPI 호출이 됐지만, 응답이 비정상적으로 반환된 경우
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("FastAPI 응답 오류: 상태코드 = " + response.getStatusCode());
            }

            return response.getBody();

        } catch (RestClientException e) {
            //예외 발생 시 예외 감싸서 던짐. 요청 자체가 실패한 경우.
            throw new RuntimeException("FastAPI 동화 생성 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
