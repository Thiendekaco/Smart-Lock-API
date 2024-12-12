package com.labmanager.project.dao.member;

import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;

import java.util.List;

public interface MemberRepository {

    void save(Member member);

    Member findById(int id );

    String deleteMemberById( int id );

    Member updateMemberById (Member member, int id );

    List<Member> findByName (String name );

    Member findByEmail ( String email );

    List<Member> findAll ();

    List<RoleMember> getListLabJoined (String emailOfMember );

}
