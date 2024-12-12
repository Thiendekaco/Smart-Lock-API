package com.labmanager.project.dao.member;

import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RoleMemberRepositoryImpl implements RoleMemberRepository{

    private EntityManager entityManager;

    @Autowired
    public RoleMemberRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(RoleMember roleMember) {
        roleMember.setTimeJoined(LocalDateTime.now());
        entityManager.persist(roleMember);
    }

    @Override
    @Transactional
    public void deleteRelationMember(String email, String nameLab) {
        RoleMember roleMember = findMemberInLab(email, nameLab);

        if(roleMember != null){
            entityManager.remove(roleMember);
        }
    }


    public LaboratoryDetail findLaboratoryDetailByName(String name) {
        TypedQuery<LaboratoryDetail> result =
                entityManager.createQuery("SELECT lab.laboratoryDetail from LaboratoryGeneral lab where nameLab like :name", LaboratoryDetail.class);
        result.setParameter("name", "%" + name + "%");

        try {
            return result.getSingleResult();
        }catch (NoResultException  e) {
            return null;
        }

    }

    @Override
    @Transactional
    public RoleMember updateStatusJoined(String emailUser, String nameLab) {
        RoleMember roleMember = findMemberInLab(emailUser, nameLab);
        if (roleMember != null){
            roleMember.setStatusJoined("success");
            entityManager.merge(roleMember);
        }
        return roleMember;
    }



    @Override
    public RoleMember findMemberInLab(String email, String nameLab) {
        TypedQuery<RoleMember> result = entityManager.createQuery("Select r from RoleMember r where member.user.emailUser = : emailMember " +
                "and laboratoryDetail.laboratoryGeneral.nameLab =: nameLab ", RoleMember.class);
        result.setParameter("emailMember", email);
        result.setParameter("nameLab", nameLab);

        try {
            return  result.getSingleResult();
        }catch (NoResultException e){
            return null;
            //exception handling
        }

    }

    @Override
    public List<RoleMember> findMemberListByLab(String nameLab) {
        TypedQuery<RoleMember> result = entityManager.createQuery("Select r from RoleMember r where " +
                " laboratoryDetail.laboratoryGeneral.nameLab =: nameLab ", RoleMember.class);
        result.setParameter("nameLab", nameLab);

        try {
            return  result.getResultList();
        }catch (NoResultException e){
            return null;
            //exception handling
        }
    }

    @Override
    public List<RoleMember> findLabsListByMember(String emailUser) {
        TypedQuery<RoleMember> result = entityManager.createQuery("Select r from RoleMember r where member.user.emailUser = : emailUser "
                , RoleMember.class);
        result.setParameter("emailUser", emailUser);


        try {
            return  result.getResultList();
        }catch (NoResultException e){
            return null;
            //exception handling
        }
    }
    public Member findMemberById(int id) {
        return entityManager.find(Member.class, id);
    }

    @Override
    @Transactional
    public RoleMember updateRoleOfMember(String emailUser, String nameLab, String role) {
        RoleMember roleMember = findMemberInLab(emailUser, nameLab);
        LaboratoryDetail  laboratoryDetail = findLaboratoryDetailByName(nameLab);
        if (roleMember != null && laboratoryDetail.getRoles().contains(role)){
            entityManager.merge(roleMember);
        }
        return roleMember;
    }
}
