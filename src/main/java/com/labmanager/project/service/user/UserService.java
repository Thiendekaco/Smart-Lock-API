package com.labmanager.project.service.user;

import com.labmanager.project.entity.user.User;

public interface UserService {

    void save (User user);

    public String deleteUserById(int theId) ;

    User findById(int theId);
}
