package com.labmanager.project.service.member;

import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;

import java.util.List;

public interface RoleMemberService {

    RoleMember AddMemberJoined (  String emailUser, String nameLab, String role );

    void DeleteMemberJoined ( String emailUser, String nameLab );

    RoleMember updateStatusJoined ( String emailUser, String nameLab);

    List<RoleMember> findMemberIsPending ( String nameLab);

    List<LaboratoryDetail> findLabIsPending (String emailUser);

    List<LaboratoryDetail> findLabIsSuccess (String emailUser);

    RoleMember updateRoleOfMember ( String emailUser, String nameLab, String role);
}
