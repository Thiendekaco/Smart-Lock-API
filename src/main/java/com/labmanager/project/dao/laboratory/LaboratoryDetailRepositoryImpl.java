package com.labmanager.project.dao.laboratory;

import com.labmanager.project.entity.laboratory.LaboratoryDetail;
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
public class LaboratoryDetailRepositoryImpl implements LaboratoryDetailRepository {

    private EntityManager entityManager;

    @Autowired
    public LaboratoryDetailRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(LaboratoryDetail laboratoryDetail) {
        entityManager.persist(laboratoryDetail);
    }

    @Override
    public LaboratoryDetail findByName(String name) {
        TypedQuery<LaboratoryDetail> result =
                entityManager.createQuery("SELECT lab.laboratoryDetail from LaboratoryGeneral lab where lab.nameLab like :name", LaboratoryDetail.class);
        result.setParameter("name", "%" + name + "%");

        try {
            return result.getSingleResult();
        }catch (NoResultException  e) {
            return null;
        }

    }

    @Override
    @Transactional
    public String deleteLaboratoryByName(String name) {
        return null;
    }

    @Override
    public LaboratoryDetail findById(int theId) {
        return entityManager.find(LaboratoryDetail.class, theId);
    }

    @Override
    @Transactional
    public LaboratoryDetail updateLaboratoryDetail(String name, LaboratoryDetail laboratoryDetail) {
        LaboratoryDetail laboratoryDetailFound = this.findByName(name);

        if(laboratoryDetail != null){
            entityManager.merge(laboratoryDetailFound.setLaboratory(laboratoryDetail));
        }
        return laboratoryDetailFound;
    }

    @Override
    @Transactional
    public void addNewRoles(String name, String role) {
        LaboratoryDetail laboratoryDetailFound = findByName(name);
        if(laboratoryDetailFound != null){
            laboratoryDetailFound.addRoles(role);
        }
    }

    @Override
    public List<RoleMember> getListMemberInLab(String nameLab) {
        LaboratoryDetail laboratoryDetail = findByName(nameLab);

        if(laboratoryDetail != null){
            return laboratoryDetail.getMembers();
        }

        return new ArrayList<RoleMember>(0);
    }

}
