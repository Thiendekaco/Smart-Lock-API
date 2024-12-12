package com.labmanager.project.dao.member;


import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private EntityManager entityManager;

    @Autowired
    public MemberRepositoryImpl(EntityManager theEntityManager ){
        this.entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Member member) {
        entityManager.persist(member);
    }

    @Override
    public Member findById(int id) {
        return entityManager.find(Member.class, id);
    }

    @Override
    @Transactional
    public String deleteMemberById(int id) {
        Member member = findById(id);
        if(member != null){
            entityManager.remove(member);
            return "Deleted Member";
        }

        return "Can't Delete Member";

    }

    @Override
    @Transactional
    public Member updateMemberById(Member memberUpdate, int id) {
        Member member = findById(id);
        if(member == null) {
            entityManager.persist(memberUpdate);
            return null;
        }

        member.setAllFeatureMember(memberUpdate);

        entityManager.merge(member);
         return  member;
    }


    @Override
    public List<Member> findByName(String name) {
        TypedQuery<Member> query = entityManager.createQuery("FROM Member WHERE name like :nameMember", Member.class);

        query.setParameter("nameMember", "%" + name + "%");

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }


    }

    @Override
    public List<Member> findAll() {
        TypedQuery<Member> query = entityManager.createQuery("FROM Member", Member.class);
        System.out.println("passed");
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            System.out.println("error");
            return null;
        }
    }
    @Override
    public Member findByEmail(String email) {
        TypedQuery<Member> result = entityManager.createQuery("SELECT u.member from User u where u.emailUser = :email", Member.class);

        result.setParameter("email", email);

        try{
            return result.getSingleResult();
        }catch (NoResultException e){
            return  null;
        }

    }

    @Override
    public List<RoleMember> getListLabJoined(String emailOfMember) {
        Member member = findByEmail(emailOfMember);
//
//        if(member != null){
//            return member.getRoleMemberList();
//        }

        return new ArrayList<>(0);
    }


}
