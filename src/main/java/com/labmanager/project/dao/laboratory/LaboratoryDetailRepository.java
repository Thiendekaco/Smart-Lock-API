package com.labmanager.project.dao.laboratory;

import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;

import java.util.List;


public interface LaboratoryDetailRepository {

    void save(LaboratoryDetail laboratoryDetail);


    LaboratoryDetail findByName ( String name );


    String deleteLaboratoryByName ( String name );

    LaboratoryDetail findById ( int theId ) ;


    LaboratoryDetail updateLaboratoryDetail (String nameLab, LaboratoryDetail laboratoryDetail);

    void addNewRoles ( String name, String role );


    List<RoleMember> getListMemberInLab ( String nameLab);

}
