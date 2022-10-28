package com.okta.password_inline_hook.controller;

import com.okta.password_inline_hook.dto.OktaRequest;
import com.okta.password_inline_hook.dto.OktaResponse;
import com.okta.password_inline_hook.service.PasswordInlineHookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCredentialsController {

    private Logger logger = LoggerFactory.getLogger(UserCredentialsController.class);
    @Autowired
    private PasswordInlineHookService passwordInlineHookService;

    @PostMapping("/passwordImport")
    public OktaResponse comparePassword(@RequestBody OktaRequest oktaRequest){
        logger.info("Okta request received to the comparePassword method. " + oktaRequest.toString());
        return this.passwordInlineHookService.checkCredentials(oktaRequest);
    }
}
