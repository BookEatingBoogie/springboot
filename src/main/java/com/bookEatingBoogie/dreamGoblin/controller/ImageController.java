package com.bookEatingBoogie.dreamGoblin.controller;

import com.bookEatingBoogie.dreamGoblin.DTO.ImgPromptDTO;
import com.bookEatingBoogie.dreamGoblin.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stableDiffusion")
public class ImageController {

    @Autowired
    private ImageGenerationService imageGenerationService;

    @PostMapping("/illust")
    public ResponseEntity<String> requestIllust(@RequestBody ImgPromptDTO imgPrompt) {

        try {
            //characterGenerationService 호출. = 캐릭터 생성 서비스 호출.
            String response =imageGenerationService.generateImage(imgPrompt);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            //응답 실패 시 예외처리.
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이미지 생성 실패: "+e.getMessage());
        }
    }

}
