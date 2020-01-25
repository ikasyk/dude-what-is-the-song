package org.int20h.dudewhatisthesong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class AuddMusicRecongnitionByLyricsResponse {
    private String status;

    private List<Result> result;

    @Data
    static public class Result {
        private String artist;

        @JsonProperty("title_with_featured")
        private String title;

        private String album;

        @Setter
        private String media;

        @Getter
        private List<Media> parsedMedia;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        static public class Media {
            private String provider;

            private String url;
        }
    }
}

