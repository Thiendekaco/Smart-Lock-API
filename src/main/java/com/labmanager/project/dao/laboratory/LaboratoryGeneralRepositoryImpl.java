package com.labmanager.project.dao.laboratory;

import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.laboratory.LaboratoryGeneral;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LaboratoryGeneralRepositoryImpl implements LaboratoryGeneralRepository {

    private EntityManager entityManager;

    @Autowired
    public LaboratoryGeneralRepositoryImpl(EntityManager theEntityManager ){
        this.entityManager = theEntityManager;
    }


    @Override
    @Transactional
    public void save(LaboratoryGeneral laboratoryGeneral) {
        entityManager.persist(laboratoryGeneral);
    }

    @Override
    public LaboratoryGeneral findById(int theId) {
        return entityManager.find(LaboratoryGeneral.class, theId);
    }

    @Override
    public List<LaboratoryGeneral> findAll() {

        try {
            TypedQuery<LaboratoryGeneral> query = entityManager.createQuery("select l FROM LaboratoryGeneral l ", LaboratoryGeneral.class);
            return query.getResultList();

        } catch (NoResultException | NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<LaboratoryGeneral> findByName(String name) {
        System.out.println(name);
        TypedQuery<LaboratoryGeneral> result =
                entityManager.createQuery("from LaboratoryGeneral where nameLab like: name", LaboratoryGeneral.class);
        result.setParameter("name", "%" + name + "%");

        try {
            return result.getResultList();
        }catch (NoResultException | NullPointerException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public LaboratoryGeneral updateLabGeneralByName(String name, LaboratoryGeneral laboratoryGeneral) {
        LaboratoryGeneral laboratoryGeneralFound = findByName(name).get(0);
        if (laboratoryGeneralFound != null){
            entityManager.merge(laboratoryGeneralFound.setAllFeature(laboratoryGeneral));
        }else{
            entityManager.persist(laboratoryGeneral);
        }

        return laboratoryGeneralFound;

    }

    @Override
    @Transactional
    public void deleteLabGeneralByName(String name) {
        LaboratoryGeneral laboratoryGeneral = findByName(name).get(0);
        if(laboratoryGeneral !=  null){
            entityManager.remove(laboratoryGeneral);
        }
    }

    @Override
    public LaboratoryDetail getLaboratoryDetail(String name) {
        LaboratoryGeneral laboratoryGeneral = findByName(name).get(0);
        if(laboratoryGeneral !=  null){
            return laboratoryGeneral.getLaboratoryDetail();
        }
        return null;
    }
}


