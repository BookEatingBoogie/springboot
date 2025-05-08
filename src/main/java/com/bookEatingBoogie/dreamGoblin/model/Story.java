package com.bookEatingBoogie.dreamGoblin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "story")
public class Story {

    @Id
    @Column(name = "storyID", columnDefinition = "VARCHAR(36)")
    private String storyId = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creationID", nullable = false)
    private Creation creation;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "favorite")
    private boolean favorite = false;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "coverImg")
    private String coverImg;

    @Column(name = "content")
    private String content;


}
