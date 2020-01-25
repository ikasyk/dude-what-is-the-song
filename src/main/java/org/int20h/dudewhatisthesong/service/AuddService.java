package org.int20h.dudewhatisthesong.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.int20h.dudewhatisthesong.entity.Song;
import org.int20h.dudewhatisthesong.model.AuddMusicRecongnitionByFileResponse;
import org.int20h.dudewhatisthesong.model.AuddMusicRecongnitionByLyricsResponse;
import org.int20h.dudewhatisthesong.utils.AuddUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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

    @Autowired
    private AuddUtils auddUtils;

    public Song findSongByLyrics(String lyrics) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("q", lyrics);
        params.add("return", auddMusicServiceList);
        params.add("api_token", auddApiToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        requestsHelper.fixRequestUserAgent(headers);

        HttpEntity<?> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<AuddMusicRecongnitionByLyricsResponse> response = restTemplate.exchange(
                auddBaseUrl + auddFindByLyricsUrl,
                HttpMethod.POST,
                httpEntity,
                AuddMusicRecongnitionByLyricsResponse.class);

        return mapAuddsFindByLyricsResponse(response.getBody());
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
        ResponseEntity<AuddMusicRecongnitionByFileResponse> response = restTemplate.exchange(
                auddBaseUrl,
                HttpMethod.POST,
                httpEntity,
                AuddMusicRecongnitionByFileResponse.class);

        return mapAuddsFindByFileResponse(response.getBody());
    }

    private Song mapAuddsFindByFileResponse(AuddMusicRecongnitionByFileResponse response) {
        AuddMusicRecongnitionByFileResponse.Result result = response.getResult();

        return new Song(
                result.getArtist(),
                result.getTitle(),
                result.getAlbum(),
                result.getAppleMusic().getUrl());

    }

    private Song mapAuddsFindByLyricsResponse(AuddMusicRecongnitionByLyricsResponse response) {
        AuddMusicRecongnitionByLyricsResponse.Result result = response.getResult().get(0);

        try {
            result.setParsedMedia(Arrays.asList(auddUtils.parseMedia(result.getMedia())));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Song(
                result.getArtist(),
                result.getTitle(),
                result.getAlbum(),
                result.getParsedMedia().get(0).getUrl());

    }
}
