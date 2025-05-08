package com.bookEatingBoogie.dreamGoblin.controller;

import com.bookEatingBoogie.dreamGoblin.DTO.CharacterRequestDTO;
import com.bookEatingBoogie.dreamGoblin.service.CharacterGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterGenerationController {

    @Autowired
    private CharacterGenerationService characterGenerationService;

    @PostMapping("/character")
    public ResponseEntity<String> requestCharacter(@RequestBody CharacterRequestDTO request) {
        try {
            // 캐릭터 이미지 생성 및 저장 경로 요청.
            String charImg = characterGenerationService.generateCharacter(request.getUserImg());
            // 이미지 저장 경로 반환(s3)
            return ResponseEntity.ok(charImg);
        } catch (RuntimeException e) {
            //응답 실패 시 예외처리.
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("캐릭터 생성 실패: "+e.getMessage());
        }
    }
}
