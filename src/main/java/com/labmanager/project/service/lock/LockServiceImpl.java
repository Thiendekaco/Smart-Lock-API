package com.labmanager.project.service.lock;


import com.labmanager.project.dao.authentication.AuthenticationRepository;
import com.labmanager.project.dao.lock.LockRepository;
import com.labmanager.project.entity.authentication.AuthenticationEntity;
import com.labmanager.project.entity.lock.LockEntity;
import com.labmanager.project.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LockServiceImpl implements LockService {

    private LockRepository lockRepository;
    private AuthenticationRepository authenticationRepository;


    @Autowired
    public LockServiceImpl(LockRepository lockRepository, AuthenticationRepository authenticationRepository) {
        this.lockRepository = lockRepository;
        this.authenticationRepository = authenticationRepository;
    }


    @Override
    public String createNewLock(String name, String modelId) throws Exception{
        LockEntity lockEntity = new LockEntity();
        lockEntity.setName(name);
        lockEntity.setModelId(modelId);
        lockEntity.setLocked(false);
        lockEntity.setRemainingOpen(0);

        String privateKey = UUID.randomUUID().toString();
        String secretKey = "SECRET_KEY";


        lockRepository.save(lockEntity);


        String privateKeyEncrypted = AESUtil.encrypt(privateKey, secretKey);
        AuthenticationEntity authenticationEntity = new AuthenticationEntity();
        authenticationEntity.setPrivateKey(privateKeyEncrypted);
        authenticationEntity.setDurationTime(30);

        authenticationRepository.save(authenticationEntity);


        return privateKey;
    }

    @Override
    public LockEntity updateLockIsLocked(String modelId, boolean isLocked) {
        return lockRepository.updateLockIsLocked(modelId, isLocked);
    }

    @Override
    public LockEntity increaseLockRemainingOpen(String modelId) {
        return lockRepository.increaseLockRemainingOpen(modelId);
    }

    @Override
    public LockEntity resetRemainingOpen(String modelId) {
        return lockRepository.resetRemainingOpen(modelId);
    }

    @Override
    public Boolean updateActiveAt(String modelId) {
         lockRepository.updateActiveAt(modelId);

         return true;
    }

    @Override
    public LockEntity findByModelId(String modelId) {
        return lockRepository.findByModelId(modelId);
    }
}
