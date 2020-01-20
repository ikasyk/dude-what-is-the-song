package org.int20h.dudewhatisthesong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuddMusicRecongnitionResponse {
    private String status;

    private Result result;

    @Data
    static public class Result {
        private String artist;

        private String title;

        private String album;

        @JsonProperty("apple_music")
        private AppleMusic appleMusic;

        @Data
        static public class AppleMusic {
            private Previews[] previews;

            private String url;

            @Data
            static public class Previews {
                private String url;
            }
        }
    }
}

