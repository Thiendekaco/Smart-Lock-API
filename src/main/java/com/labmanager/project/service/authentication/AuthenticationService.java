package com.labmanager.project.service.authentication;

import com.labmanager.project.entity.authentication.AuthenticationEntity;

public interface AuthenticationService {
    AuthenticationEntity changeMasterPassword(String modelId, String masterPassword);

    AuthenticationEntity updateDurationTime(String modelId, int durationTime);

    public String resetMasterPassword(String PrivateKey) throws Exception;

    String getPrivateKey(String modelId, String masterPassword) throws Exception;

    String createCodeQrUri(String modelId, String masterPassword) throws Exception;

    Boolean VerifyOTP(String modelId, String otp) throws Exception;
}
