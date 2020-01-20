package org.int20h.dudewhatisthesong.service;

import org.int20h.dudewhatisthesong.entity.Song;
import org.int20h.dudewhatisthesong.model.AuddMusicRecongnitionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuddService {
    public static final String AUDD_BASE_URL = "https://api.audd.io";
    public static final String AUDD_RETURN_MUSIC_TYPES = "apple_music,deezer,spotify";

    @Value("${audd.api-token}")
    private String auddApiToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestsHelper requestsHelper;

    public Song findMusicByFile(Resource resource) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", resource);
        params.add("return", AUDD_RETURN_MUSIC_TYPES);
        params.add("api_token", auddApiToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        requestsHelper.fixRequestUserAgent(headers);

        HttpEntity<?> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<AuddMusicRecongnitionResponse> response = restTemplate.exchange(AUDD_BASE_URL, HttpMethod.POST, httpEntity, AuddMusicRecongnitionResponse.class);

        AuddMusicRecongnitionResponse body = response.getBody();

        return mapAuddResponseToSong(body);
    }

    private Song mapAuddResponseToSong(AuddMusicRecongnitionResponse response) {
        AuddMusicRecongnitionResponse.Result result = response.getResult();

        Song song = new Song(
                result.getArtist(),
                result.getTitle(),
                result.getAlbum(),
                result.getAppleMusic().getUrl()
        );

        return song;
    }
}
