package com.labmanager.project.service.authentication;

import com.labmanager.project.dao.authentication.AuthenticationRepository;
import com.labmanager.project.dao.lock.LockRepository;
import com.labmanager.project.entity.authentication.AuthenticationEntity;
import com.labmanager.project.entity.lock.LockEntity;
import com.labmanager.project.utils.AESUtil;
import dev.samstevens.totp.code.*;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.samstevens.totp.qr.QrData;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private LockRepository lockRepository;
    private AuthenticationRepository authenticationRepository;

    @Autowired
    public AuthenticationServiceImpl(LockRepository lockRepository, AuthenticationRepository authenticationRepository) {
        this.lockRepository = lockRepository;
        this.authenticationRepository = authenticationRepository;
    }


    @Override
    public AuthenticationEntity changeMasterPassword(String modelId, String masterPassword) {

        LockEntity lockEntity = lockRepository.findByModelId(modelId);
        AuthenticationEntity authenticationEntity = lockEntity.getAuthentication();
        String privateKey = authenticationEntity.getPrivateKey();

        return authenticationRepository.changePassword(privateKey, masterPassword);
    }

    @Override
    public AuthenticationEntity updateDurationTime(String modelId, int durationTime) {
        LockEntity lockEntity = lockRepository.findByModelId(modelId);
        AuthenticationEntity authenticationEntity = lockEntity.getAuthentication();
        String privateKey = authenticationEntity.getPrivateKey();

        return authenticationRepository.updateDurationTime(privateKey, durationTime);
    }

    @Override
    public String resetMasterPassword(String PrivateKey) throws Exception {
        String secretKey = "SECRET_KEY";
        String privateKeyEncrypted = AESUtil.encrypt(PrivateKey, secretKey);
        AuthenticationEntity authenticationEntity = authenticationRepository.findByPrivateKey(privateKeyEncrypted);
        String privateKey = authenticationEntity.getPrivateKey();

        authenticationEntity.setUpdatePasswordAt(LocalDateTime.now());
        String newMasterPassword = UUID.randomUUID().toString();

        authenticationRepository.changePassword(privateKey, newMasterPassword);

        return newMasterPassword;
    }

    @Override
    public String getPrivateKey(String modelId, String masterPassword) throws Exception {
        LockEntity lockEntity = lockRepository.findByModelId(modelId);
        AuthenticationEntity authenticationEntity = lockEntity.getAuthentication();
        String privateKey = authenticationEntity.getPrivateKey();
        String password = authenticationEntity.getMasterPassword();

        if (!password.equals(masterPassword)) {
            throw new Exception("Master password is not correct");
        }

        String secretKey = "SECRET_KEY";

        return AESUtil.decrypt(privateKey, secretKey);
    }

    @Override
    public String createCodeQrUri(String modelId, String masterPassword) throws Exception {
        LockEntity lockEntity = lockRepository.findByModelId(modelId);
        AuthenticationEntity authenticationEntity = lockEntity.getAuthentication();
        String privateKeyEncrypted = authenticationEntity.getPrivateKey();
        String password = authenticationEntity.getMasterPassword();

        if (!password.equals(masterPassword)) {
            throw new Exception("Master password is not correct");
        }

        String secretKey = "SECRET_KEY";

        String privateKey = AESUtil.decrypt(privateKeyEncrypted, secretKey);
        String secret = AESUtil.encrypt(authenticationEntity.getMasterPassword(), privateKey);

        QrData data = new QrData.Builder()
                .label(modelId)
                .secret(secret)
                .issuer("HUST")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(authenticationEntity.getDurationTime())
                .build();

        return data.getUri();
    }

    @Override
    public Boolean VerifyOTP(String modelId, String otp) throws Exception {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        LockEntity lockEntity = lockRepository.findByModelId(modelId);

        AuthenticationEntity authenticationEntity = lockEntity.getAuthentication();
        String privateKeyEncrypted = authenticationEntity.getPrivateKey();
        String secretKey = "SECRET_KEY";

        String privateKey = AESUtil.decrypt(privateKeyEncrypted, secretKey);
        String secret = AESUtil.encrypt(authenticationEntity.getMasterPassword(), privateKey);


        boolean isSuccess =  verifier.isValidCode(secret, otp);
        if (isSuccess) {
            lockRepository.resetRemainingOpen(modelId);
            lockRepository.updateLockIsLocked(modelId, false);
        } else {
            lockRepository.increaseLockRemainingOpen(modelId);
        }

        return isSuccess;
    }
}
