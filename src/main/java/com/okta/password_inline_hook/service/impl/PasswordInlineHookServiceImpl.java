package com.okta.password_inline_hook.service.impl;

import com.okta.password_inline_hook.dto.Command;
import com.okta.password_inline_hook.dto.OktaRequest;
import com.okta.password_inline_hook.dto.OktaResponse;
import com.okta.password_inline_hook.dto.Value;
import com.okta.password_inline_hook.service.McKessonService;
import com.okta.password_inline_hook.service.PasswordInlineHookService;
import com.okta.password_inline_hook.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordInlineHookServiceImpl implements PasswordInlineHookService {

    @Autowired
    private McKessonService mcKessonService;

    private Logger logger = LoggerFactory.getLogger(PasswordInlineHookServiceImpl.class);
    @Override
    public OktaResponse checkCredentials(OktaRequest oktaRequest) {
        OktaResponse oktaResponse = new OktaResponse();
        Boolean isAvailable = this.mcKessonService.isAvailable(oktaRequest.getData().getContext().getCredential().getUsername(),oktaRequest.getData().getContext().getCredential().getPassword());
        Command command = new Command();
        command.setType(Constant.COMMAND_TYPE_ACTION_UPDATE);
        List<Command> commandList = new ArrayList();
        Value value = new Value();
        if(isAvailable.booleanValue()){
            value.setCredential(Constant.CREDENTIAL_STATUS_VERIFIED);
            logger.info("User verified");
        }else{
            value.setCredential(Constant.CREDENTIAL_STATUS_UNVERIFIED);
            logger.info("User unverified");
        }
        command.setValue(value);
        commandList.add(command);
        oktaResponse.setCommands(commandList);
        return oktaResponse;
    }
}
