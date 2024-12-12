package com.labmanager.project.dao.laboratory;

import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.laboratory.LaboratoryGeneral;

import java.util.List;

public interface LaboratoryGeneralRepository {


    void save (LaboratoryGeneral laboratoryGeneral);

    LaboratoryGeneral findById (int theId);

    List<LaboratoryGeneral> findAll ();

    List<LaboratoryGeneral> findByName ( String name );

    LaboratoryGeneral updateLabGeneralByName ( String name, LaboratoryGeneral laboratoryGeneral);

    void deleteLabGeneralByName ( String name);

    LaboratoryDetail getLaboratoryDetail ( String name);


}
