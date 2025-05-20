package com.bookEatingBoogie.dreamGoblin.controller;

import com.bookEatingBoogie.dreamGoblin.DTO.CharacterDTO;
import com.bookEatingBoogie.dreamGoblin.DTO.StorageDTO;
import com.bookEatingBoogie.dreamGoblin.service.LoadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/mypage")
public class LoadingController {

    @Autowired
    private LoadingService loadingService;

    //보관함 내용 반환
    @GetMapping("/story")
    public StorageDTO loadStorageController() {
        return loadingService.loadStorage("user");
    }
    //캐릭터 보관함 내용 반환
    @GetMapping("/character")
    public List<CharacterDTO> loadCharacter() {
        return loadingService.loadCharacters("user");
    }

    @GetMapping("/story/delete")
    public String deleteStory() {
        return loadingService.deleteStoryById(storyId, userId);
        return ResponseEntity.ok("스토리가 삭제되었습니다.");
    }
}
