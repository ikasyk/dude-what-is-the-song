package org.int20h.dudewhatisthesong.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.int20h.dudewhatisthesong.model.AuddMusicRecongnitionByLyricsResponse.Result.Media;
import org.springframework.stereotype.Component;

@Component
public class AuddUtils {

    ObjectMapper mapper = new ObjectMapper();


    public Media[] parseMedia(String json) throws JsonProcessingException {

        Media[] list = mapper.readValue(json, Media[].class);


        System.out.println(list);

        return list;

    }
}
