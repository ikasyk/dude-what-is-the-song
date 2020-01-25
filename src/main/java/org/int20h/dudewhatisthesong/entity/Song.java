package org.int20h.dudewhatisthesong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class Song {
    private String artist;

    private String title;

    @Nullable
    private String album;

    @Nullable
    private String appleMusicLink;
}
