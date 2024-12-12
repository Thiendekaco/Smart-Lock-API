package com.labmanager.project.rest.member;


import com.labmanager.project.dto.MemberDTO;
import com.labmanager.project.dto.RoleMemberDTO;
import com.labmanager.project.entity.laboratory.LaboratoryGeneral;
import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;
import com.labmanager.project.service.member.MemberService;
import com.labmanager.project.service.member.RoleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.labmanager.project.rest.member.RoleMemberRestController.getMemberDTO;

@RestController
@RequestMapping("/api")
public class MemberRestController {

    private MemberService memberService;
    private RoleMemberService roleMemberService;

    @Autowired
    public MemberRestController(MemberService memberService, RoleMemberService roleMemberService) {
        this.memberService = memberService;
        this.roleMemberService = roleMemberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> findAll() {

        List<Member> memberList = memberService.findAll();

        List<MemberDTO> memberDTOList = memberList.stream().map(this::ConvertEntityToDTO).toList();

        return  ResponseEntity.ok(memberDTOList);

    }


    @GetMapping("/members/profile/{memberId}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable int memberId){
        Member member = memberService.findById(memberId);

        if(member == null){
            throw new RuntimeException("Employee id not found - " + memberId);
        }

        return ResponseEntity.ok(ConvertEntityToDTO(member));
    }

    @GetMapping("/members/profile")
    public ResponseEntity<List<MemberDTO>> getMemberByName(@RequestParam("name") String nameMember){
        List<Member> memberList = memberService.findByName(nameMember);

        if(memberList == null){
            throw new RuntimeException("Employee id not found - " + nameMember);
        }

        List<MemberDTO> memberDTOList = memberList.stream().map(this::ConvertEntityToDTO).toList();

        return  ResponseEntity.ok(memberDTOList);
    }



    @DeleteMapping("/members/{memberId}")
    public String deleteMember(@PathVariable int memberId){
        return memberService.deleteById(memberId);
    }



    @PutMapping("/members/{memberId}")
    public ResponseEntity<MemberDTO> updateEmployee(@PathVariable int memberId, @RequestBody Member theMemberUpdate) {
        System.out.println(memberId + " " + theMemberUpdate);

        Member member =  memberService.updateMemberById(theMemberUpdate, memberId);

        return  ResponseEntity.ok(ConvertEntityToDTO(member));
    }


    @DeleteMapping("/member/{memberId}")
    public String deleteEmployee(@PathVariable int memberId) {

        Member tempMember = memberService.findById(memberId);

        // throw exception if null

        if (tempMember == null) {
            throw new RuntimeException("Employee id not found - " + memberId);
        }

        memberService.deleteById(memberId);

        return "Deleted employee id - " + memberId;
    }

    private MemberDTO ConvertEntityToDTO (Member member) {

        return getMemberDTO(member, roleMemberService);
    }
}