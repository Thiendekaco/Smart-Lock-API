package com.labmanager.project.service.laboratory;

import com.labmanager.project.dao.laboratory.LaboratoryGeneralRepository;
import com.labmanager.project.dao.member.MemberRepository;
import com.labmanager.project.dao.member.RoleMemberRepository;
import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.laboratory.LaboratoryGeneral;
import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;
import com.labmanager.project.service.member.MemberService;
import com.labmanager.project.service.member.RoleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LaboratoryGeneralServiceImpl implements LaboratoryGeneralService{

    private LaboratoryGeneralRepository laboratoryGeneralRepo;
    private MemberRepository memberRepo;

    private RoleMemberRepository roleMemberRepo;


    @Autowired
    public LaboratoryGeneralServiceImpl(LaboratoryGeneralRepository laboratoryGeneralRepo, MemberRepository memberService, RoleMemberRepository roleMemberRepo) {
        this.laboratoryGeneralRepo = laboratoryGeneralRepo;
        this.memberRepo = memberService;
        this.roleMemberRepo = roleMemberRepo;
    }


    @Override
    public void createNewLaboratory(String emailMember, LaboratoryGeneral laboratoryGeneral) {
        LaboratoryDetail laboratoryDetail = laboratoryGeneral.getLaboratoryDetail();

        List<String> roles = laboratoryDetail.getRoles();
        if(roles == null){
            roles = new ArrayList<String>();
        }
        roles.add("admin");
        roles.add("member");
        laboratoryDetail.setRoles(roles);
        laboratoryDetail.setNumberOfMember(1);
        laboratoryDetail.setDescription("");
        laboratoryGeneral.setLaboratoryDetail(laboratoryDetail);
        laboratoryGeneralRepo.save(laboratoryGeneral);
        LaboratoryGeneral laboratoryGeneralAfterSave = laboratoryGeneralRepo.findByName(laboratoryGeneral.getNameLab()).get(0);
        Member  memberCreateLab = memberRepo.findByEmail(emailMember);
        RoleMember relationMember = new RoleMember(memberCreateLab, laboratoryGeneralAfterSave.getLaboratoryDetail(), "admin", "success" );
        roleMemberRepo.save(relationMember);
        laboratoryGeneral.getLaboratoryDetail().addMemberJoinedLab(relationMember);
        laboratoryGeneralRepo.updateLabGeneralByName(laboratoryGeneral.getNameLab(), laboratoryGeneral);
    }

    @Override
    public LaboratoryGeneral updateLaboratory(LaboratoryGeneral laboratoryGeneral, String nameLab) {
        return laboratoryGeneralRepo.updateLabGeneralByName(nameLab, laboratoryGeneral);
    }

    @Override
    public List<LaboratoryGeneral> findByName(String nameLab) {
        return laboratoryGeneralRepo.findByName(nameLab);
    }

    @Override
    public List<LaboratoryGeneral> findAll() {
        return laboratoryGeneralRepo.findAll();
    }

    @Override
    public void deleteLaboratory(String nameLab) {
        laboratoryGeneralRepo.deleteLabGeneralByName(nameLab);
    }
}
