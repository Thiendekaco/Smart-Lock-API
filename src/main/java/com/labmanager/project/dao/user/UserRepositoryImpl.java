package com.labmanager.project.dao.user;

import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User user) {
        User checkUser = this.findUserByEmail(user.getEmailUser());

        if (checkUser == null){
            this.entityManager.persist(user);
        }

    }

    @Override
    @Transactional
    public String deleteById(int theId) {
        User user = this.findUserById(theId);

        if(user != null){
            entityManager.remove(user);

            return "Deleted user";
        }

        return "Can't delete user";
    }

    @Override
    public User findUserById(int theId) {

        return entityManager.find(User.class, theId);
    }

    @Override
    public User findUserByEmail(String email) {
        TypedQuery<User> result = entityManager.createQuery("SELECT u from User u where u.emailUser = :email", User.class);

        result.setParameter("email", email);

        try{
            return result.getSingleResult();
        }catch (NoResultException e){
            return  null;
        }

    }
}
