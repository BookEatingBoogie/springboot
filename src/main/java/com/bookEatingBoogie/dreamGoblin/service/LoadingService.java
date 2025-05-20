package com.bookEatingBoogie.dreamGoblin.service;

import com.bookEatingBoogie.dreamGoblin.DTO.CharacterDTO;
import com.bookEatingBoogie.dreamGoblin.DTO.StorageDTO;
import com.bookEatingBoogie.dreamGoblin.DTO.StoryDTO;
import com.bookEatingBoogie.dreamGoblin.Repository.CharacterRepository;
import com.bookEatingBoogie.dreamGoblin.Repository.CreationRepository;
import com.bookEatingBoogie.dreamGoblin.Repository.StoryRepository;
import com.bookEatingBoogie.dreamGoblin.Repository.UserRepository;
import com.bookEatingBoogie.dreamGoblin.model.Characters;
import com.bookEatingBoogie.dreamGoblin.model.Creation;
import com.bookEatingBoogie.dreamGoblin.model.Story;
import com.bookEatingBoogie.dreamGoblin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public StorageDTO deleteStory(String storyId, String userId) {
        System.out.println(storyId);
        Story story = storyRepository.findByStoryIdWithAllRelations(storyId)
                .orElseThrow(() -> new IllegalArgumentException("스토리를 찾을 수 없습니다."));

        // 사용자 확인 (보안)
        String ownerId = story.getCreation().getCharacters().getUser().getUserId();
        if (!ownerId.equals(userId)) {
            throw new SecurityException("본인의 스토리만 삭제할 수 있습니다.");
        }

        // 연관 객체들
        Creation creation = story.getCreation();
        Characters character = creation.getCharacters();

        storyRepository.deleteByStoryId(storyId);

        // 2. 연결된 Creation에 다른 스토리가 없으면 삭제
        if (!storyRepository.existsByCreation(creation)) {
            creationRepository.delete(creation);
        }
        // 3. 연결된 캐릭터에 다른 Creation이 없으면 캐릭터 삭제
        if (!creationRepository.existsByCharacters(character)) {
            characterRepository.delete(character);
        }

        return loadStorage(userId);
    }
}
