package com.smuralee.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public Map<String, String> getDefaultResponse() {
        log.info("Default response for the root context");
        HashMap<String, String> map = new HashMap<>();
        map.put("status", "Success");
        map.put("message", "API context root invoked");
        return map;
    }
}
