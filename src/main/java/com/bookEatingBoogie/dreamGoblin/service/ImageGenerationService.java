package com.bookEatingBoogie.dreamGoblin.service;

import com.bookEatingBoogie.dreamGoblin.DTO.ImgPromptDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ImageGenerationService {

    @Value("${fastapi.baseUrl}")
    private String baseUrl;

    public String generateImage(ImgPromptDTO prompt) {

        //endpoint 경로 설정.
        String fastAPIUrl = baseUrl + "/image";


        // FastAPI 호출 기본 로직
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        /*
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("prompt", prompt);
         */
        // 프롬프트 전송
        HttpEntity<ImgPromptDTO> entity = new HttpEntity<>(prompt, headers);

        try {
            /* FastAPI 서버에 POST 요청 보내기.
             * 응답은 String 형태로 받는다*/
            ResponseEntity<String> response = restTemplate.postForEntity(fastAPIUrl, entity, String.class);

            // fastAPI 호출이 됐지만, 응답이 비정상적으로 반환된 경우
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("FastAPI 응답 오류: 상태코드 = " + response.getStatusCode());
            }
            //성공 시 응답 반환.
            return response.getBody();

        } catch (RestClientException e) {
            //예외 발생 시 예외 감싸서 던짐. 요청 자체가 실패한 경우.
            throw new RuntimeException("FastAPI 이미지 생성 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
