package com.labmanager.project.service.laboratory;

import com.labmanager.project.entity.laboratory.LaboratoryGeneral;

import java.util.List;

public interface LaboratoryGeneralService {

    void createNewLaboratory (String emailMember , LaboratoryGeneral laboratoryGeneral);

    LaboratoryGeneral updateLaboratory ( LaboratoryGeneral laboratoryGeneral, String nameLab);

    List<LaboratoryGeneral> findByName( String nameLab );

    List<LaboratoryGeneral> findAll();

    void deleteLaboratory ( String nameLab );
}
