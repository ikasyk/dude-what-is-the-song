package org.int20h.dudewhatisthesong.service;

import org.int20h.dudewhatisthesong.entity.Song;
import org.int20h.dudewhatisthesong.model.AuddMusicRecongnitionByLyricsResult;
import org.int20h.dudewhatisthesong.model.AuddMusicRecongnitionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class AuddService {

    @Value("${audd.base-url}")
    public String auddBaseUrl;

    @Value("${audd.find-by-lyrics-url}")
    public String auddFindByLyricsUrl;

    @Value("${audd.return-music-service-list-url}")
    public String auddMusicServiceList;

    @Value("${audd.api-token}")
    private String auddApiToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestsHelper requestsHelper;

    public Song findSongByLyrics(String lyrics) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("q", lyrics);
        params.add("api_token", auddApiToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        requestsHelper.fixRequestUserAgent(headers);

        HttpEntity<?> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<AuddMusicRecongnitionByLyricsResult> response = restTemplate.exchange(
                auddBaseUrl + auddFindByLyricsUrl,
                HttpMethod.POST,
                httpEntity,
                AuddMusicRecongnitionByLyricsResult.class);

        AuddMusicRecongnitionByLyricsResult body = response.getBody();

        AuddMusicRecongnitionByLyricsResult.Result[] results = body.getResult();
        if (results.length == 0) {
            return null;
        }

        AuddMusicRecongnitionByLyricsResult.Result result = results[0];

        Song song = new Song(
                result.getArtist(),
                result.getTitle(),
                null,
                null,
                result.getLyrics()
        );

        return song;
    }

    public Song findSongByFile(Resource resource) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", resource);
        params.add("return", auddMusicServiceList);
        params.add("api_token", auddApiToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        requestsHelper.fixRequestUserAgent(headers);

        HttpEntity<?> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<AuddMusicRecongnitionResponse> response = restTemplate.exchange(
                auddBaseUrl,
                HttpMethod.POST,
                httpEntity,
                AuddMusicRecongnitionResponse.class);

        return mapAuddResponseToSong(response.getBody());
    }

    private Song mapAuddResponseToSong(AuddMusicRecongnitionResponse response) {
        AuddMusicRecongnitionResponse.Result result = response.getResult();

        return new Song(
                result.getArtist(),
                result.getTitle(),
                result.getAlbum(),
                result.getAppleMusic().getUrl(),
                null
        );
    }

}
