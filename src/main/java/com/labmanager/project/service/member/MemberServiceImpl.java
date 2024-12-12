package com.labmanager.project.service.member;

import com.labmanager.project.dao.member.MemberRepository;
import com.labmanager.project.entity.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> findByName(String name) {
        List<Member> membersList = memberRepository.findByName(name);

        return membersList == null ? new ArrayList<>(0) : membersList;

    }

    @Override
    public Member findById(int theId) {
        Member result = memberRepository.findById(theId);

        Member theMember = null;

        if (result != null) {
            theMember = result;
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }

        return theMember;
    }

    @Override
    public void save(Member theMember) {
        memberRepository.save(theMember);
    }

    @Override
    public String deleteById(int theId) {
        return memberRepository.deleteMemberById(theId);
    }

    @Override
    public Member updateMemberById(Member member, int id) {
        return memberRepository.updateMemberById(member, id);

    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
