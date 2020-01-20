package org.int20h.dudewhatisthesong.controller;

import lombok.extern.slf4j.Slf4j;
import org.int20h.dudewhatisthesong.entity.Song;
import org.int20h.dudewhatisthesong.model.AuddMusicRecongnitionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@RestController
@Slf4j
public class SongController {
    @Value("${audd.api-token}")
    private String auddApiToken;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/byLyrics")
    public Song getSongByLyrics(String lyrics) {
        // TODO some func that find song
        return null;
    }

    @PostMapping(value = "/searchByFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Song searchByFile(@RequestPart("file") MultipartFile file) throws IOException {
        log.debug("Received file size: {}", file.getSize());

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", file.getResource());
        params.add("return", "apple_music,deezer,spotify");
        params.add("api_token", auddApiToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.put("User-Agent", Collections.singletonList("Dude-What-Is-The-Song-App"));

        HttpEntity<?> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<AuddMusicRecongnitionResponse> response = restTemplate.exchange("https://api.audd.io", HttpMethod.POST, httpEntity, AuddMusicRecongnitionResponse.class);

        AuddMusicRecongnitionResponse body = response.getBody();

        return new Song(
                body.getResult().getArtist(),
                body.getResult().getTitle(),
                body.getResult().getAlbum(),
                body.getResult().getAppleMusic().getUrl()
        );
    }


}
