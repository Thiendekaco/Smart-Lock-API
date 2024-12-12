package com.labmanager.project.service.laboratory;

import com.labmanager.project.dao.laboratory.LaboratoryDetailRepository;
import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.member.RoleMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LaboratoryDetailServiceImpl implements LaboratoryDetailService{

    private LaboratoryDetailRepository laboratoryDetailRepository;

    @Autowired
    public LaboratoryDetailServiceImpl (LaboratoryDetailRepository laboratoryDetailRepository){
        this.laboratoryDetailRepository = laboratoryDetailRepository;
    }

    @Override
    public LaboratoryDetail updateLaboratoryDetail(LaboratoryDetail laboratoryDetail, String nameLab) {
        return laboratoryDetailRepository.updateLaboratoryDetail(nameLab, laboratoryDetail);
    }

    @Override
    public LaboratoryDetail findByLaboratoryDetailByName( String nameLab ) {
        return laboratoryDetailRepository.findByName(nameLab);
    }

    @Override
    public List<RoleMember> getListMember(String nameLab) {

        LaboratoryDetail laboratoryDetailRepo = laboratoryDetailRepository.findByName(nameLab);

        return new ArrayList<RoleMember>();
    }

    @Override
    public List<String> getRolesOfLab(String nameLab) {

        LaboratoryDetail laboratoryDetailRepo = findByLaboratoryDetailByName(nameLab);

        return laboratoryDetailRepo.getRoles();
    }

    @Override
    public List<String> updateRoleOfLab(String nameLab, String role) {

        LaboratoryDetail laboratoryDetail = laboratoryDetailRepository.findByName(nameLab);
        List<String> roles = laboratoryDetail.getRoles();

        if(roles.contains(role)){
            roles.removeIf(role_ -> !role_.equals(role));
        }else{
            roles.add(role);
        }

        laboratoryDetail.setRoles(roles);
        laboratoryDetailRepository.updateLaboratoryDetail(nameLab, laboratoryDetail);

        return roles;
    }
}
