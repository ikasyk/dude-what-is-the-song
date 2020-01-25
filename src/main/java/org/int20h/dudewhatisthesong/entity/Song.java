package org.int20h.dudewhatisthesong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Song {
    private String artist;

    private String title;

    private String album;

    private String appleMusicLink;
}
