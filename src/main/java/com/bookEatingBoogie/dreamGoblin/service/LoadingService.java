package com.bookEatingBoogie.dreamGoblin.service;

import com.bookEatingBoogie.dreamGoblin.DTO.CharacterDTO;
import com.bookEatingBoogie.dreamGoblin.DTO.StorageDTO;
import com.bookEatingBoogie.dreamGoblin.DTO.StoryDTO;
import com.bookEatingBoogie.dreamGoblin.Repository.CharacterRepository;
import com.bookEatingBoogie.dreamGoblin.Repository.CreationRepository;
import com.bookEatingBoogie.dreamGoblin.Repository.StoryRepository;
import com.bookEatingBoogie.dreamGoblin.Repository.UserRepository;
import com.bookEatingBoogie.dreamGoblin.model.Characters;
import com.bookEatingBoogie.dreamGoblin.model.Story;
import com.bookEatingBoogie.dreamGoblin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class LoadingService {

    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreationRepository creationRepository;

    public StorageDTO loadStorage(String userId) {

        // 1) 유저 조회
        User user = userRepository.findById("user")
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 2) 유저별 스토리·캐릭터 조회 & DTO 매핑
        List<StoryDTO> stories = storyRepository.findAllByUserIdWithCharacters("user")
                .stream()
                .map(StoryDTO::new)
                .toList();

        //List<CreationDTO> creations = creationRepository.findByCreationId(stories.getCreationId());

        List<CharacterDTO> chars = characterRepository.findByUser(user)
                .stream()
                .map(CharacterDTO::new)
                .toList();

        // 3) DTO에 담아서 반환
            return new StorageDTO(stories, chars);
    }

    public List<CharacterDTO> loadCharacters(String userId) {
        // 1) 유저 조회
        User user = userRepository.findById("user")
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        return characterRepository.findByUser(user)
                .stream()
                .map(CharacterDTO::new)
                .toList();
    }

    public List<StoryDTO> deleteStory(String storyId, String userId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("스토리를 찾을 수 없습니다."));

        // 사용자 확인 (보안)
        String ownerId = story.getCreation().getCharacters().getUser().getUserId();
        if (!ownerId.equals(userId)) {
            throw new SecurityException("본인의 스토리만 삭제할 수 있습니다.");
        }

        storyRepository.delete(story);
        if story.getCreation().
        return loadStorage("user").getStories();
    }
}
