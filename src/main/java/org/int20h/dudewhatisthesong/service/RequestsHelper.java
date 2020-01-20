package org.int20h.dudewhatisthesong.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class RequestsHelper {
    public HttpHeaders fixRequestUserAgent(HttpHeaders httpHeaders) {
        httpHeaders.put("User-Agent", Collections.singletonList("Dude-What-Is-The-Song-App"));
        return httpHeaders;
    }
}
