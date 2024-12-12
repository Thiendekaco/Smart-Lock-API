package com.labmanager.project.service.laboratory;

import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.member.RoleMember;

import java.util.List;

public interface LaboratoryDetailService {

    LaboratoryDetail updateLaboratoryDetail(LaboratoryDetail laboratoryDetail, String nameLab );

    LaboratoryDetail findByLaboratoryDetailByName ( String nameLab);

    List<RoleMember> getListMember ( String nameLab);

    List<String> getRolesOfLab ( String nameLab );

    List<String> updateRoleOfLab ( String nameLab, String role );

}
