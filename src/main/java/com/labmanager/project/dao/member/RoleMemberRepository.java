package com.labmanager.project.dao.member;

import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;

import java.util.List;

public interface RoleMemberRepository {

    void save (RoleMember roleMember);

    void deleteRelationMember ( String email, String nameLab );


    RoleMember updateStatusJoined ( String email, String nameLab);

    RoleMember findMemberInLab (String member, String nameLab);

    List<RoleMember> findMemberListByLab ( String email );

    List<RoleMember> findLabsListByMember ( String email );

    RoleMember updateRoleOfMember ( String email, String nameLab, String role);
}
