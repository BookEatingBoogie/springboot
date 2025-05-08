package com.bookEatingBoogie.dreamGoblin.controller;

import com.bookEatingBoogie.dreamGoblin.DTO.GptPromptDTO;
import com.bookEatingBoogie.dreamGoblin.DTO.StoryInfoDTO;
import com.bookEatingBoogie.dreamGoblin.service.CharacterGenerationService;
import com.bookEatingBoogie.dreamGoblin.service.StoryGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gpt")
public class FastAPIController {

    @Autowired
    private CharacterGenerationService characterGenerationService;
    @Autowired
    private StoryGenerationService storyGenerationService;

    //캐릭터 생성 관련 컨트롤러
    @PostMapping("/no")
    public ResponseEntity<String> requestCharacter(@RequestBody(required = false) GptPromptDTO gptPrompt) {

        String userRequest = "The child has come to create a main character! Welcome the child and ask the first question about the character's name to start the story creation.";

        //사용자 입력값이 없는 경우 빈 문자열 넣어주기
        if(gptPrompt == null) {
            gptPrompt = new GptPromptDTO();
            gptPrompt.setUserContent(userRequest);
        } else if(gptPrompt.getUserContent() == null) {
            gptPrompt.setUserContent(userRequest);
        }

        try {
            //characterGenerationService 호출. = 캐릭터 생성 서비스 호출.
            String response = characterGenerationService.generateCharacter("abc");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            //응답 실패 시 예외처리.
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("캐릭터 생성 실패: "+e.getMessage());
        }
    }

    //스토리 생성 관련 컨트롤러.
    @PostMapping("/story")
    public ResponseEntity<?> requestStory(@RequestBody StoryInfoDTO storyInfo) {

        try {
            //storyGenerationService 호출. = 스토리 생성 서비스 호출.
            List<String> response = storyGenerationService.generateStory(storyInfo);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            //응답 실패 시 예외처리.
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("동화 생성 실패: "+e.getMessage());
        }
    }
}
