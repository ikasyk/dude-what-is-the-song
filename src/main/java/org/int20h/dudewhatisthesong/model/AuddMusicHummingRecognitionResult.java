package org.int20h.dudewhatisthesong.model;

import lombok.Data;

@Data
public class AuddMusicHummingRecognitionResult {
    private Result result;

    @Data
    public static class Result {
        private SongResult[] list;

        @Data
        public static class SongResult {
            private String artist;
            private String title;
        }
    }
}
