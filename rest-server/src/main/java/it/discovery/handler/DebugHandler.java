package it.discovery.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DebugHandler {

    public void log(HttpServletRequest req) {
        log.info(STR."New request with URI: \{req.getRequestURI()}");
    }
}
