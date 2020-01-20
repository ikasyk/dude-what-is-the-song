package org.int20h.dudewhatisthesong.controller;

import lombok.extern.slf4j.Slf4j;
import org.int20h.dudewhatisthesong.entity.Song;
import org.int20h.dudewhatisthesong.service.AuddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class SongController {
    @Autowired
    private AuddService auddService;

    @GetMapping("/byLyrics")
    public Song getSongByLyrics(String lyrics) {
        // TODO some func that find song
        return null;
    }

    /**
     * Finds music in Audd API by file
     * @param file music file in supported format (mp3, m4a, ogg etc.)
     * @return song data (artist, album, title, Apple Music link)
     * @throws IOException when exception in file
     */
    @PostMapping(value = "/searchByFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Song searchByFile(@RequestPart("file") MultipartFile file) throws IOException {
        log.debug("Received file size: {}", file.getSize());

        return auddService.findMusicByFile(file.getResource());
    }


}
