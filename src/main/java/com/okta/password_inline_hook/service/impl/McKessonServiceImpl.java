package com.okta.password_inline_hook.service.impl;

import com.okta.password_inline_hook.dto.McKessonRequest;
import com.okta.password_inline_hook.dto.McKessonResponse;
import com.okta.password_inline_hook.service.McKessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class McKessonServiceImpl implements McKessonService {

    @Value("${mckesson.url}")
    private String url;
    @Value("${mckesson.auth.key}")
    private String authorizationKey;

    private Logger logger = LoggerFactory.getLogger(McKessonServiceImpl.class);
    @Override
    public Boolean isAvailable(String username, String password) {
        logger.info("Checking availability for user : "+username);
        url = url.replace("profileName",username);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authorizationKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        McKessonRequest mcKessonRequest = new McKessonRequest();
        mcKessonRequest.setPassword(password);
        HttpEntity<McKessonRequest> request = new HttpEntity<>(mcKessonRequest,headers);
        ResponseEntity<McKessonResponse> response = restTemplate
                .exchange(url,HttpMethod.POST, request, McKessonResponse.class);
        logger.info("Is user available : "+response.getBody().getSuccess());
        return response.getBody().getSuccess();
    }
}
