package com.labmanager.project.service.member;

import com.labmanager.project.dao.laboratory.LaboratoryDetailRepository;
import com.labmanager.project.dao.member.MemberRepository;
import com.labmanager.project.dao.member.RoleMemberRepository;
import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoleMemberServiceImpl implements RoleMemberService {

    private RoleMemberRepository roleMemberRepo;
    private MemberRepository memberRepo;
    private LaboratoryDetailRepository laboratoryDetailRepo;


    @Autowired
    public RoleMemberServiceImpl(RoleMemberRepository roleMemberRepo, MemberRepository memberRepo, LaboratoryDetailRepository laboratoryDetailRepo) {
        this.roleMemberRepo = roleMemberRepo;
        this.memberRepo = memberRepo;
        this.laboratoryDetailRepo = laboratoryDetailRepo;
    }



    @Override
    public RoleMember AddMemberJoined( String emailUser, String nameLab, String role) {
        Member member = memberRepo.findByEmail(emailUser);
        LaboratoryDetail laboratoryDetail = laboratoryDetailRepo.findByName(nameLab);

        RoleMember roleMemberCheck = roleMemberRepo.findMemberInLab
                (emailUser, nameLab);

        if(roleMemberCheck == null && member != null && laboratoryDetail != null){
            roleMemberRepo.save(new RoleMember(member, laboratoryDetail, role));
            System.out.println("1231231");
            return roleMemberRepo.findMemberInLab
                    (emailUser, nameLab);
        }
        return  roleMemberCheck;
    }

    @Override
    public void DeleteMemberJoined(String emailUser, String nameLab) {
        roleMemberRepo.deleteRelationMember(emailUser, nameLab);
    }

    @Override
    public RoleMember updateStatusJoined(String emailUser, String nameLab) {
        return roleMemberRepo.updateStatusJoined(emailUser, nameLab);
    }


    @Override
    public List<RoleMember> findMemberIsPending( String nameLab) {
        List<RoleMember> roleMemberList = roleMemberRepo.findMemberListByLab(nameLab);

        if(roleMemberList != null){
            return roleMemberList
                    .stream()
                    .filter(roleMember -> Objects.equals(roleMember.getStatusJoined(), "pending"))
                    .toList();
        }


        return new ArrayList<RoleMember>(0);
    }

    @Override
    public List<LaboratoryDetail> findLabIsPending(String emailUser) {
        List<RoleMember> roleLabList = roleMemberRepo.findLabsListByMember(emailUser);

        if(roleLabList != null){
            return roleLabList
                    .stream()
                    .filter(roleMember -> Objects.equals(roleMember.getStatusJoined(), "pending"))
                    .map(RoleMember::getLaboratoryDetail).toList();
        }


        return new ArrayList<LaboratoryDetail>(0);
    }
    @Override
    public List<LaboratoryDetail> findLabIsSuccess(String emailUser) {
        List<RoleMember> roleLabList = roleMemberRepo.findLabsListByMember(emailUser);

        if(roleLabList != null){
            return roleLabList
                    .stream()
                    .filter(roleMember -> Objects.equals(roleMember.getStatusJoined(), "success"))
                    .map(RoleMember::getLaboratoryDetail).toList();
        }


        return new ArrayList<LaboratoryDetail>(0);
    }



    @Override
    public RoleMember updateRoleOfMember(String emailUser, String nameLab, String role) {
        return roleMemberRepo.updateRoleOfMember(emailUser, nameLab, role);
    }
}
