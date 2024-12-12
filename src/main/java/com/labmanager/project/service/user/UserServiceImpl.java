package com.labmanager.project.service.user;

import com.labmanager.project.dao.user.UserRepository;
import com.labmanager.project.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public  UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Override
    public String deleteUserById(int theId) {
        return this.userRepository.deleteById(theId);
    }

    @Override
    public User findById(int theId) {
        return this.userRepository.findUserById(theId);
    }
}
