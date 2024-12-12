package com.labmanager.project.dao.user;

import com.labmanager.project.entity.user.User;

public interface UserRepository {

    void save(User user);

    String deleteById( int theId ) ;

    User findUserById( int theId );

    User findUserByEmail ( String email ) ;

}
