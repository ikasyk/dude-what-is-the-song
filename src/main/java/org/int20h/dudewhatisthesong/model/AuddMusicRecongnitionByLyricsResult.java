package org.int20h.dudewhatisthesong.model;

import lombok.Data;

@Data
public class AuddMusicRecongnitionByLyricsResult {
    String status;

    Result result[];

    @Data
    static public class Result {
        private String artist;

        private String title;

        private String lyrics;
    }
}
