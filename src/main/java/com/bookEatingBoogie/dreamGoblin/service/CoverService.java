package com.bookEatingBoogie.dreamGoblin.service;

import com.bookEatingBoogie.dreamGoblin.Repository.StoryRepository;
import com.bookEatingBoogie.dreamGoblin.model.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoverService {

    @Autowired
    private StoryRepository storyRepository;

    @Transactional
    public void saveCoverInfo(String userId, String title, String coverImg, int creationId) {
        Story story = storyRepository.findByUserIdAndCreationId(userId, creationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 creationId로 스토리를 찾을 수 없습니다."));

        story.setTitle(title);
        story.setCoverImg(coverImg);

        storyRepository.save(story);
    }

}
