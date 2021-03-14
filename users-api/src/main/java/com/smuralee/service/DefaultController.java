package com.smuralee.service;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@XRayEnabled
@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public Map<String, String> getDefaultResponse(HttpServletRequest request) throws UnknownHostException {
        log.info("Default response for the root context");
        HashMap<String, String> map = new HashMap<>();
        map.put("Status", "Success");
        map.put("Remote address", request.getRemoteAddr());
        map.put("Host name", InetAddress.getLocalHost().getHostName());
        return map;
    }
}
