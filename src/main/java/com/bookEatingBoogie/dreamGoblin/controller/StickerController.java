package com.bookEatingBoogie.dreamGoblin.controller;
import com.bookEatingBoogie.dreamGoblin.service.StickerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StickerController {

    private final StickerService stickerService;

    public StickerController(StickerService stickerService) {
        this.stickerService = stickerService;
    }

    // 프론트에서 스티커 배열 요청: GET /sticker/list
    @GetMapping("/sticker/list")
    public ResponseEntity<List<String>> getStickers() {
        List<String> stickers = stickerService.getStickerList();
        return ResponseEntity.ok(stickers);
    }
}