package org.int20h.dudewhatisthesong.controller;

import lombok.extern.slf4j.Slf4j;
import org.int20h.dudewhatisthesong.entity.Song;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1")
@Slf4j
public class SongController {

    @GetMapping("/byLyrics")
    public Song getSongByLyrics(String lyrics){
        // TODO some func that find song
        return null;
    }
}
