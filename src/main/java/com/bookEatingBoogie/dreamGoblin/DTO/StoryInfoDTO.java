package com.bookEatingBoogie.dreamGoblin.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StoryInfoDTO {
    //private int charID;
    private String genre;
    private String place;
    private String mood;
    private String helper;
    private String villain;
}
