package org.int20h.dudewhatisthesong.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.int20h.dudewhatisthesong.entity.Song;
import org.int20h.dudewhatisthesong.service.AuddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Api(value = "/song", description = "Songs main controller")
@RequestMapping("/song")
@Slf4j
public class SongController {
    @Autowired
    private AuddService auddService;

    @GetMapping("/searchByLyrics")
    @ApiOperation(value = "Finds music in Audd API by lyrics. Returns song data (artist, title)",
            response = Song.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Result with information of song (artist, title, lyrics)"),
            @ApiResponse(code = 404, message = "On error during recognition")
    })
    public Song getSongByLyrics(@ApiParam(value = "Lyrics of song")
                                @RequestParam("l") String lyrics) {
        return auddService.findSongByLyrics(lyrics);
    }

    @PostMapping(value = "/searchByFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Finds music in Audd API by file. Returns song data (artist, album, title, Apple Music link)",
            response = Song.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Result with information of song (artist, album, title, Apple Music link)"),
            @ApiResponse(code = 404, message = "On error during recognition")
    })
    public Song searchByFile(@ApiParam(value = "Record of music in supported format (mp3, m4a, ogg etc.)")
                             @RequestPart("file") MultipartFile file) throws IOException {
        log.debug("Received file size: {}", file.getSize());
        return auddService.findSongByFile(file.getResource());
    }
}
